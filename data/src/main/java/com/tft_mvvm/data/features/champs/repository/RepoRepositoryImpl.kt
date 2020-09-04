package com.tft_mvvm.data.features.champs.repository

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.example.common_jvm.function.Either.Companion.runSuspendWithCatchError
import com.tft_mvvm.data.exception_interceptor.RemoteExceptionInterceptor
import com.tft_mvvm.data.features.champs.mapper.*
import com.tft_mvvm.data.features.champs.service.ApiService
import com.tft_mvvm.data.local.database.ChampDAO
import com.tft_mvvm.data.local.database.ClassAndOriginDAO
import com.tft_mvvm.data.local.database.ItemDAO
import com.tft_mvvm.data.local.database.TeamDAO
import com.tft_mvvm.data.local.model.ChampListDBO
import com.tft_mvvm.data.local.model.ItemListDBO
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.model.ClassAndOriginListEntity
import com.tft_mvvm.domain.features.model.TeamBuilderListEntity
import com.tft_mvvm.domain.features.model.TeamListEntity
import com.tft_mvvm.domain.features.repository.RepoRepository

class RepoRepositoryImpl(
    private val apiService: ApiService,
    private val champDAO: ChampDAO,
    private val teamDAO: TeamDAO,
    private val itemDAO: ItemDAO,
    private val itemDaoEntityMapper: ItemDaoEntityMapper,
    private val itemListMapper: ItemListMapper,
    private val classAndOriginDAO: ClassAndOriginDAO,
    private val teamListMapper: TeamListMapper,
    private val teamDaoEntityMapper: TeamDaoEntityMapper,
    private val remoteExceptionInterceptor: RemoteExceptionInterceptor,
    private val champListMapper: ChampListMapper,
    private val classAndOriginDaoEntityMapper: ClassAndOriginDaoEntityMapper,
    private val champDaoEntityMapper: ChampDaoEntityMapper

) : RepoRepository {

    private fun createChamp(
        champDBO: ChampListDBO.ChampDBO,
        star: String,
        listItem: List<ChampListEntity.Champ.Item>
    ): ChampListEntity.Champ {
        return ChampListEntity.Champ(
            id = champDBO.id,
            cost = champDBO.cost,
            linkImg = champDBO.linkImg,
            activated = champDBO.activated,
            linkChampCover = champDBO.linkChampCover,
            originAndClassName = champDBO.originAndClassName.split(","),
            skillName = champDBO.skillName,
            linkSkillAvatar = champDBO.linkSkillAvatar,
            rankChamp = champDBO.rankChamp,
            suitableItem = listItem,
            star = star,
            name = champDBO.name
        )
    }

    override suspend fun getAllChamps(isForceLoadData: Boolean): Either<Failure, ChampListEntity> =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            if (isForceLoadData) {
                reloadChampDataFromNetwork()
            } else {
                val localChamps: List<ChampListDBO.ChampDBO> = champDAO.getAllChamp()
                if (localChamps.isEmpty()) {
                    reloadChampDataFromNetwork()
                }
            }

            val localChamps = champDAO.getAllChamp()
            val dbAfterInset = champListMapper.mapList(localChamps)
            val champListEntity = ChampListEntity(champs = dbAfterInset)
            return@runSuspendWithCatchError Either.Success(champListEntity)
        }

    private suspend fun reloadChampDataFromNetwork() {
        val listChampResponse = apiService.getChampList()
        val listChampDbo = champDaoEntityMapper.map(listChampResponse)
        champDAO.deleteAllChampTable()
        champDAO.insertChamps(listChampDbo.champDBOs)
    }

    private suspend fun reloadTeamDataFormNetwork() {
        val teamListResponse = apiService.getTeamList()
        val teamListDBO = teamDaoEntityMapper.map(teamListResponse)
        teamDAO.deleteAllTeam()
        teamDAO.insertTeam(teamListDBO.teamDBOs)
    }

    private suspend fun reloadItemDataFromNetwork() {
        val listItemResponse = apiService.getItemListResponse()
        val listItemDBO = itemDaoEntityMapper.map(listItemResponse)
        itemDAO.insertItems(listItemDBO.items)
    }

    override suspend fun getTeams(isForceLoadData: Boolean) =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            if (isForceLoadData) {
                reloadTeamDataFormNetwork()
            } else {
                val localTeam = teamDAO.getAllTeam()
                if (localTeam.isEmpty())
                    reloadTeamDataFormNetwork()
            }
            val dbTeamListEntity =
                TeamListEntity(
                    teams = teamListMapper.mapList(
                        teamDAO.getAllTeam()
                    )
                )
            insertItemDataIntoRoom()
            val listTeamBuilder: List<TeamBuilderListEntity.TeamsBuilder> =
                mapListTeam(dbTeamListEntity, itemDAO.getAllItem(), champDAO.getAllChamp())
            return@runSuspendWithCatchError Either.Success(
                TeamBuilderListEntity(
                    teamBuilders = listTeamBuilder
                )
            )
        }

    private suspend fun insertItemDataIntoRoom() {
        if (itemDAO.getAllItem().isEmpty()) {
            val listItemResponse = apiService.getItemListResponse()
            val listItemDBO = itemDaoEntityMapper.map(listItemResponse)
            itemDAO.insertItems(listItemDBO.items)
        }
    }

    fun getListItem(
        listItem: List<ItemListDBO.ItemDBO>,
        listId: List<String>
    ): List<ChampListEntity.Champ.Item> {
        val listItemResult = mutableListOf<ItemListDBO.ItemDBO>()
        for (item in listItem) {
            for (id in listId) {
                if (item.itemId == id) {
                    listItemResult.add(item)
                }
            }
        }
        return itemListMapper.mapList(listItemResult)
    }

    fun getListChampByListIdInternal(
        listChamp: List<ChampListDBO.ChampDBO>,
        listId: List<String>
    ): List<ChampListDBO.ChampDBO> {
        val listChampResult = mutableListOf<ChampListDBO.ChampDBO>()
        for (champ in listChamp) {
            for (id in listId) {
                if (champ.id == id) {
                    listChampResult.add(champ)
                }
            }
        }
        return listChampResult
    }

    fun getChampByIdInternal(
        listChamp: List<ChampListDBO.ChampDBO>,
        id: String
    ): ChampListDBO.ChampDBO {
        val champ = listChamp.findLast { champDBO -> champDBO.id == id }
        if (champ != null) {
            return champ
        } else {
            return ChampListDBO.ChampDBO(
                name = "",
                id = "",
                suitableItem = "",
                rankChamp = "",
                linkImg = "",
                skillName = "",
                linkChampCover = "",
                activated = "",
                cost = "",
                originAndClassName = "",
                linkSkillAvatar = ""
            )
        }
    }

    fun mapListTeam(
        dbTeamListEntity: TeamListEntity,
        listItemDBO: List<ItemListDBO.ItemDBO>,
        listChampDBO: List<ChampListDBO.ChampDBO>
    ): List<TeamBuilderListEntity.TeamsBuilder> {
        val listTeamBuilder = mutableListOf<TeamBuilderListEntity.TeamsBuilder>()
        for (team in dbTeamListEntity.teams) {
            val listChamp = mutableListOf<ChampListEntity.Champ>()
            for (idChamp in team.listIdChamp.indices) {
                val listItem = mutableListOf<ChampListEntity.Champ.Item>()
                if (team.listIdSuitable[idChamp].isNotEmpty()) {
                    val listIdItem = team.listIdSuitable[idChamp].split(",")
                    listItem.addAll(getListItem(listItemDBO, listIdItem))
                }
                listChamp.add(
                    createChamp(
                        getChampByIdInternal(listChampDBO, team.listIdChamp[idChamp]),
                        team.listStar[idChamp],
                        listItem
                    )
                )
            }
            listTeamBuilder.add(
                TeamBuilderListEntity.TeamsBuilder(
                    name = team.nameTeam,
                    champEntity = ChampListEntity(listChamp)
                )
            )
        }
        return listTeamBuilder
    }


    override suspend fun getClassAndOriginContent(
        listClassOrOriginName: List<String>
    ) = runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
        if (classAndOriginDAO.getAllClassAndOrigin().isEmpty()) {
            val classAndOriginDBO =
                classAndOriginDaoEntityMapper.map(apiService.getClassAndOriginList())
            classAndOriginDAO.insertClassAndOrigin(classAndOriginDBO.classAndOrigins)
        }
        val listClassAndOriginContent = mutableListOf<ClassAndOriginListEntity.ClassAndOrigin>()
        listClassOrOriginName.forEach { classOrOriginName ->
            val content = classAndOriginDAO.getClassOrOriginByName(classOrOriginName)
            val listChampDbo = champDAO.getListChampByClassOrOriginName(classOrOriginName)
            val champs = champListMapper.mapList(listChampDbo)
            listClassAndOriginContent.add(
                ClassAndOriginListEntity.ClassAndOrigin(
                    classOrOriginName = content.classOrOriginName,
                    champEntity = ChampListEntity(champs = champs),
                    content = content.content,
                    bonus = content.bonus
                )
            )
        }
        return@runSuspendWithCatchError Either.Success(ClassAndOriginListEntity(listClassAndOrigin = listClassAndOriginContent))

    }

    override suspend fun getChampById(id: String) =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            if (itemDAO.getAllItem().isEmpty()) {
                reloadItemDataFromNetwork()
            }
            val champDBO = champDAO.getChampById(id)
            val listIdItem = champDBO.suitableItem.split(",")
            val listItem = itemDAO.getItemByListId(listIdItem)
            val champ = createChamp(champDBO, "1", itemListMapper.mapList(listItem))
            return@runSuspendWithCatchError Either.Success(champ)
        }

    override suspend fun getTeamRecommendForChamp(id: String) =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            if (teamDAO.getAllTeam().isEmpty()) {
                reloadTeamDataFormNetwork()
            }
            val teamListEntity =
                TeamListEntity(
                    teams = teamListMapper.mapList(
                        teamDAO.getAllTeam()
                    )
                )
            val dbTeamListEntityResult = mutableListOf<TeamListEntity.Team>()
            for (team in teamListEntity.teams) {

                for (idChamp in team.listIdChamp) {
                    if (id == idChamp) {
                        dbTeamListEntityResult.add(team)
                    }
                }
            }
            val listTeamBuilder: List<TeamBuilderListEntity.TeamsBuilder> =
                mapListTeam(
                    TeamListEntity(dbTeamListEntityResult),
                    itemDAO.getAllItem(),
                    champDAO.getAllChamp()
                )
            return@runSuspendWithCatchError Either.Success(
                TeamBuilderListEntity(
                    teamBuilders = listTeamBuilder
                )
            )
        }
}