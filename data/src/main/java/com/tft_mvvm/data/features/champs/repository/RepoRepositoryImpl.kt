package com.tft_mvvm.data.features.champs.repository

import android.util.Log
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
import com.tft_mvvm.domain.features.model.ChampListEntity
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
    private val classAndOriginListMapper: ClassAndOriginListMapper,
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
            classs = champDBO.classs,
            origin = champDBO.origin,
            skillName = champDBO.skillName,
            linkSkillAvatar = champDBO.linkSkillAvatar,
            rankChamp = champDBO.rankChamp,
            suitableItem = listItem,
            threeStar = threeStart,
            name = champDBO.name
        )
    }

    override suspend fun getAllChamps(isForceLoadData: Boolean) =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            var listChampDBO = champDAO.getAllChamp()
            val listChampEntity = mutableListOf<ChampListEntity.Champ>()
            if (champDAO.getAllChamp().isNullOrEmpty() || isForceLoadData) {
                val listChampResponse = apiService.getChampList()
                val listChampDbo = champDaoEntityMapper.map(listChampResponse)
                if (isForceLoadData && listChampDbo.champDBOs.isNotEmpty()) {
                    champDAO.deleteAllChampTable()
                }
                champDAO.insertChamps(listChampDbo.champDBOs)
                listChampDBO = champDAO.getAllChamp()
            }

            if (itemDAO.getAllItem().isNullOrEmpty()) {
                val listItemResponse = apiService.getItemListResponse()
                val listItemDBO = itemDaoEntityMapper.map(listItemResponse)
                itemDAO.insertItems(listItemDBO.items)
            }
            for (i in listChampDBO) {
                val listItem = mutableListOf<ChampListEntity.Champ.Item>()
                if (i.suitableItem.isNotBlank()) {
                    val listIdItem = i.suitableItem.split(",")
                    for (j in listIdItem) {
                        listItem.add(itemListMapper.map(itemDAO.getItemById(j)))
                    }
                }

                listChampEntity.add(
                    createChamp(i, false, listItem)
                )
            }
            listChampEntity.size
            return@runSuspendWithCatchError Either.Success(ChampListEntity(champs = listChampEntity))
        }

    override suspend fun getChampsByOrigin(origin: String) =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            return@runSuspendWithCatchError Either.Success(
                ChampListEntity(
                    champs = champListMapper.mapList(
                        champDAO.getDataByOrigin(origin)
                    )
                )
            )
        }

    override suspend fun getChampsByClass(classs: String) =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            return@runSuspendWithCatchError Either.Success(
                ChampListEntity(
                    champs = champListMapper.mapList(
                        champDAO.getDataByClass(classs)
                    )
                )
            )
        }

    override suspend fun getTeams(isForceLoadData: Boolean) =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            var dbTeamListEntity =
                TeamListEntity(
                    teams = teamListMapper.mapList(
                        teamDAO.getAllTeam()
                    )
                )
            if (dbTeamListEntity.teams.isNullOrEmpty() || isForceLoadData) {
                val teamListResponse = apiService.getTeamList()
                val teamListDBO = teamDaoEntityMapper.map(teamListResponse)
                if (isForceLoadData && teamListDBO.teamDBOs.isNotEmpty()) {
                    teamDAO.deleteAllTeam()
                }
                teamDAO.insertTeam(teamListDBO.teamDBOs)
                dbTeamListEntity =
                    TeamListEntity(
                        teams = teamListMapper.mapList(
                            teamDAO.getAllTeam()
                        )
                    )
            }
            dbTeamListEntity.teams.size
            if (itemDAO.getAllItem().isNullOrEmpty()) {
                val listItemResponse = apiService.getItemListResponse()
                val listItemDBO = itemDaoEntityMapper.map(listItemResponse)
                itemDAO.insertItems(listItemDBO.items)
            }
            val listTeamBuilder: MutableList<TeamBuilderListEntity.TeamsBuilder> = mutableListOf()

            dbTeamListEntity.teams.forEach { team ->
                val listChampEntity = mutableListOf<ChampListEntity.Champ>()
                val listChampMainDbo = champDAO.getListChampByTeam(team.listIdChampMain)
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
                                champDAO.getChampById(idChamp),
                                true,
                                mutableListOf()
                            )
                        )
                    } else {
                        listChampEntity.add(
                            createChamp(
                                champDAO.getChampById(idChamp),
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
            return@runSuspendWithCatchError Either.Success(
                TeamBuilderListEntity(
                    listTeamBuilder
                )
            )
        }

    override suspend fun getClassAndOriginContent(
        isForceLoadData: Boolean,
        classOrOriginName: String
    ) =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            if (classAndOriginDAO.getAllClassAndOrigin().isNullOrEmpty() || isForceLoadData) {
                val classAndOriginResponses = apiService.getClassAndOriginList()
                val classAndOriginDBO = classAndOriginDaoEntityMapper.map(classAndOriginResponses)
                classAndOriginDAO.insertClassAndOrigin(classAndOriginDBO.classAndOrigins)
                val classOrOriginAfterInsert = classAndOriginListMapper.map(
                    classAndOriginDAO.getClassOrOriginByName(classOrOriginName)
                )
                return@runSuspendWithCatchError Either.Success(classOrOriginAfterInsert)
            } else {
                return@runSuspendWithCatchError Either.Success(
                    classAndOriginListMapper.map(
                        classAndOriginDAO.getClassOrOriginByName(classOrOriginName)
                    )
                )
            }
        }


    override suspend fun updateChamp(id: String) =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            val listChampDBO = champDaoEntityMapper.map(apiService.getChampList())
            listChampDBO.champDBOs.forEach { champDBO ->
                if (champDBO.id == id) {
                    champDAO.updateChamp(champDBO)
                }
            }
            return@runSuspendWithCatchError Either.Success(
                champListMapper.map(
                    champDAO.getChampById(
                        id
                    )
                )
            )
        }

    override suspend fun getChampById(id: String) =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            val champDBO = champDAO.getChampById(id)
            val listItem = mutableListOf<ChampListEntity.Champ.Item>()
            if (champDBO.suitableItem.isNotBlank()) {
                val listIdItem = champDBO.suitableItem.split(",")
                for (i in listIdItem) {
                    listItem.add(itemListMapper.map(itemDAO.getItemById(i)))
                }
            }
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
                val listItemResponse = apiService.getItemListResponse()
                val listItemDBO = itemDaoEntityMapper.map(listItemResponse)
                itemDAO.insertItems(listItemDBO.items)
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
                val listChampMainDbo = champDAO.getListChampByTeam(team.listIdChampMain)
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
                                champDAO.getChampById(idChamp),
                                true,
                                mutableListOf()
                            )
                        )
                    } else {
                        listChampEntity.add(
                            createChamp(
                                champDAO.getChampById(idChamp),
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