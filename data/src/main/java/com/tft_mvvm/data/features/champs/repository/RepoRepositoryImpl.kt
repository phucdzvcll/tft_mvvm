package com.tft_mvvm.data.features.champs.repository

import android.util.Log
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
        threeStart: Boolean,
        listItem: MutableList<ChampListEntity.Champ.Item>
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
            threeStar = threeStart,
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
                if(localTeam.isEmpty())
                reloadTeamDataFormNetwork()
            }
            val dbTeamListEntity =
                TeamListEntity(
                    teams = teamListMapper.mapList(
                        teamDAO.getAllTeam()
                    )
                )

            if (itemDAO.getAllItem().isNullOrEmpty()) {
                reloadItemDataFromNetwork()
            }
            val listTeamBuilder: MutableList<TeamBuilderListEntity.TeamsBuilder> =
                mapListTeam(dbTeamListEntity, x, y, z)
            return@runSuspendWithCatchError Either.Success(
                TeamBuilderListEntity(
                    listTeamBuilder
                )
            )
        }

    private fun mapListTeam(dbTeamListEntity: TeamListEntity): MutableList<TeamBuilderListEntity.TeamsBuilder> {
        val listTeamBuilder: MutableList<TeamBuilderListEntity.TeamsBuilder> = mutableListOf()

        dbTeamListEntity.teams.forEach { team ->
            val listChampEntity = mutableListOf<ChampListEntity.Champ>()
            val listChampMainDbo = getListChampByTeam(team.idTeam)
            val listIdChampCommon = mutableListOf<String>()
            listIdChampCommon.addAll(team.listIdChamp)
            for (position in listChampMainDbo.indices) {
                var check = 0
                for (idChampThreeStart in team.listIdChampThreeStar) {
                    if (listChampMainDbo[position].id == idChampThreeStart) {
                        check++
                        Log.d("phuc", "$check")
                    }
                }
                val listIdItem = team.listIdSuitable[position].split(",")
                val listItem =
                    itemListMapper.mapList(getItemByListId(listIdItem)) as MutableList
                if (check > 0) {
                    listChampEntity.add(createChamp(listChampMainDbo[position], true, listItem))
                } else {
                    listChampEntity.add(
                        createChamp(
                            listChampMainDbo[position],
                            false,
                            listItem
                        )
                    )
                }
                val index = mutableListOf<Int>()
                for (positionIdChampCommon in listIdChampCommon.indices) {
                    if (listChampMainDbo[position].id == listIdChampCommon[positionIdChampCommon]) {
                        index.add(positionIdChampCommon)
                    }
                }
                for (p in index) {
                    listIdChampCommon.removeAt(p)
                }
            }

            for (idChamp in listIdChampCommon) {
                var check = 0
                for (idChampThreeStart in team.listIdChampThreeStar) {
                    if (idChamp == idChampThreeStart) {
                        check++
                    }
                }
                if (check > 0) {
                    listChampEntity.add(
                        createChamp(
                            getChampByIdInternal(idChamp),
                            true,
                            mutableListOf()
                        )
                    )
                } else {
                    listChampEntity.add(
                        createChamp(
                            getChampByIdInternal(idChamp),
                            false,
                            mutableListOf()
                        )
                    )
                }
            }
            listChampEntity.sortBy { it.name }
            listChampEntity.sortBy { it.cost }
            listTeamBuilder.add(
                TeamBuilderListEntity.TeamsBuilder(
                    team.nameTeam,
                    ChampListEntity(champs = listChampEntity)
                )
            )

        }
        return listTeamBuilder
    }

    private fun getChampByIdInternal(idChamp: String): ChampListDBO.ChampDBO {

    }

    private fun getListChampByTeam(teamId: String): List<ChampListDBO.ChampDBO> {

    }

    private fun getItemByListId(listIdItem: List<String>): List<ItemListDBO.ItemDBO> {

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
            val listItem =
                itemListMapper.mapList(getItemByListId(listIdItem)) as MutableList
            val champ = createChamp(champDBO, false, listItem)
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
            val listTeamBuilder: MutableList<TeamBuilderListEntity.TeamsBuilder> = mutableListOf()
            dbTeamListEntityResult.size
            dbTeamListEntityResult.forEach { team ->
                val listIdThreeStart = team.listIdChampThreeStar
                val listChampEntity = mutableListOf<ChampListEntity.Champ>()
                val listChampMainDbo = getListChampByTeam(team.idTeam)
                val listIdChampCommon = mutableListOf<String>()
                listIdChampCommon.addAll(team.listIdChamp)
                for (position in listChampMainDbo.indices) {
                    var check = 0
                    for (idChampThreeStart in listIdThreeStart) {
                        if (listChampMainDbo[position].id == idChampThreeStart) {
                            check++
                        }
                    }
                    val listItem = mutableListOf<ChampListEntity.Champ.Item>()
                    val listIdItem = team.listIdSuitable[position].split(",")
                    for (idItem in listIdItem) {
                        val item = itemListMapper.map(itemDAO.getItemById(idItem))
                        listItem.add(item)
                    }
                    if (check > 0) {
                        listChampEntity.add(createChamp(listChampMainDbo[position], true, listItem))
                    } else {
                        listChampEntity.add(
                            createChamp(
                                listChampMainDbo[position],
                                false,
                                listItem
                            )
                        )
                    }
                    val index = mutableListOf<Int>()
                    for (positionIdChampCommon in listIdChampCommon.indices) {
                        if (listChampMainDbo[position].id == listIdChampCommon[positionIdChampCommon]) {
                            index.add(positionIdChampCommon)
                        }
                    }
                    for (p in index) {
                        listIdChampCommon.removeAt(p)
                    }
                }
                for (idChamp in listIdChampCommon) {
                    var check = 0
                    for (idChampThreeStart in team.listIdChampThreeStar) {
                        if (idChamp == idChampThreeStart) {
                            check++
                        }
                    }
                    if (check > 0) {
                        listChampEntity.add(
                            createChamp(
                                getChampByIdInternal(idChamp),
                                true,
                                mutableListOf()
                            )
                        )
                    } else {
                        listChampEntity.add(
                            createChamp(
                                getChampByIdInternal(idChamp),
                                false,
                                mutableListOf()
                            )
                        )
                    }
                }
                listChampEntity.sortBy { it.name }
                listChampEntity.sortBy { it.cost }
                listTeamBuilder.add(
                    TeamBuilderListEntity.TeamsBuilder(
                        team.nameTeam,
                        ChampListEntity(champs = listChampEntity)
                    )
                )

            }
            Log.d("phuc", "$listTeamBuilder")
            return@runSuspendWithCatchError Either.Success(TeamBuilderListEntity(teamBuilders = listTeamBuilder))
        }

}