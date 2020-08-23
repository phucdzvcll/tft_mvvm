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
import kotlinx.coroutines.*

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
        listItem: List<ItemListDBO.ItemDBO>
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
            suitableItem = itemListMapper.mapList(listItem),
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
                mapListTeamAsync(dbTeamListEntity).await()
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

    fun mapListTeamAsync(dbTeamListEntity: TeamListEntity): Deferred<List<TeamBuilderListEntity.TeamsBuilder>> {
        return CoroutineScope(Dispatchers.IO).async {
            val listTeamBuilder = mutableListOf<TeamBuilderListEntity.TeamsBuilder>()
            for (team in dbTeamListEntity.teams) {
                val listChamp = mutableListOf<ChampListEntity.Champ>()
                for (idChamp in team.listIdChamp.indices) {
                    for (idChampMain in team.listIdChampMain.indices) {
                        if (team.listIdChamp[idChamp] == team.listIdChampMain[idChampMain]) {
                            val listIdItem = team.listIdSuitable[idChampMain].split(",")
                            val listItem = itemDAO.getItemByListId(listIdItem)
                            listChamp.add(
                                createChamp(
                                    champDBO = champDAO.getChampById(team.listIdChamp[idChamp]),
                                    star = team.listIdChampThreeStar[idChamp],
                                    listItem = listItem
                                )
                            )
                        }
                    }
                }
                val listIdChampCommon = team.listIdChamp as MutableList
                for (i in team.listIdChampMain) {
                    listIdChampCommon.remove(i)
                }
                listChamp.addAll(
                    champListMapper.mapList(
                        champDAO.getListChampByTeam(
                            listIdChampCommon
                        )
                    )
                )
                listTeamBuilder.add(
                    TeamBuilderListEntity.TeamsBuilder(
                        team.nameTeam,
                        ChampListEntity(listChamp)
                    )
                )
            }
            return@async listTeamBuilder
        }
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
                    listChamp = ChampListEntity(champs = champs),
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
            val champ = createChamp(champDBO, "1", listItem)
            return@runSuspendWithCatchError Either.Success(champ)
        }

    override suspend fun getTeamRecommendForChamp(id: String) =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            var teamListEntity =
                TeamListEntity(
                    teams = teamListMapper.mapList(
                        teamDAO.getAllTeam()
                    )
                )
            if (teamListEntity.teams.isNullOrEmpty()) {
                val listTeamResponse = apiService.getTeamList()
                val listTeamDBO = teamDaoEntityMapper.map(listTeamResponse)
                teamDAO.insertTeam(listTeamDBO.teamDBOs)
                teamListEntity =
                    TeamListEntity(
                        teams = teamListMapper.mapList(
                            teamDAO.getAllTeam()
                        )
                    )
            }
            val dbTeamListEntityResult = mutableListOf<TeamListEntity.Team>()
            for (team in teamListEntity.teams) {

                for (idChamp in team.listIdChamp) {
                    if (id == idChamp) {
                        dbTeamListEntityResult.add(team)
                    }
                }
            }
            val listTeamBuilder: List<TeamBuilderListEntity.TeamsBuilder> =
                mapListTeamAsync(TeamListEntity(dbTeamListEntityResult)).await()
            return@runSuspendWithCatchError Either.Success(
                TeamBuilderListEntity(
                    teamBuilders = listTeamBuilder
                )
            )
        }

}