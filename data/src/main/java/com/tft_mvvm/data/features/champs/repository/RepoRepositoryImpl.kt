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
import com.tft_mvvm.data.local.model.ItemListDBO
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.model.ItemListEntity
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
    override suspend fun getChamps(isForceLoadData: Boolean) =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            val dbResult = ChampListEntity(
                champs = champListMapper.mapList(champDAO.getData())
            )
            if (dbResult.champs.isNullOrEmpty() || isForceLoadData) {
                if (isForceLoadData) {
                    champDAO.deleteAllChampTable()
                }
                val champListResponse = apiService.getChampList()
                val champListDBO = champDaoEntityMapper.map(champListResponse)
                champDAO.insertChamps(champListDBO.champDBOs)
                return@runSuspendWithCatchError Either.Success(
                    ChampListEntity(
                        champs = champListMapper.mapList(
                            champDAO.getData()
                        )
                    )
                )
            } else {
                return@runSuspendWithCatchError Either.Success(dbResult)
            }
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
            if (teamDAO.getAllTeam().isNullOrEmpty() || isForceLoadData) {
                teamDAO.deleteAllTeam()
                val teamListResponse = apiService.getTeamList()
                val teamListDBO = teamDaoEntityMapper.map(teamListResponse)
                teamDAO.insertTeam(teamListDBO.teamDBOs)
                dbTeamListEntity =
                    TeamListEntity(
                        teams = teamListMapper.mapList(
                            teamDAO.getAllTeam()
                        )
                    )
            }
            val listTeamBuilder: ArrayList<TeamBuilderListEntity.TeamsBuilder> = ArrayList()
            for (i in dbTeamListEntity.teams) {
                val list = i.listId.split(",")
                val champs = ChampListEntity(
                    champs = champListMapper.mapList(
                        champDAO.getListChampByTeam(list)
                    )
                )
                val teamBuilder = TeamBuilderListEntity.TeamsBuilder(i.name, champs)
                listTeamBuilder.add(teamBuilder)
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
                classAndOriginDAO.deleteAllClassAndOrinTable()
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

    override suspend fun getListSuitableItem(
        isForceLoadData: Boolean,
        listId: String
    ): Either<Failure, ItemListEntity> =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            if (listId.isNotBlank()) {
                val list = listId.split(",")
                if (itemDAO.getItemByListId(list).isNullOrEmpty() || isForceLoadData) {
                    if (isForceLoadData) {
                        itemDAO.deleteAllItemTable()
                    }
                    val listDBO = itemDaoEntityMapper.map(apiService.getItemListResponse())
                    itemDAO.insertItems(listDBO.items)
                    val dbAfterInsert = itemListMapper.mapList(itemDAO.getItemByListId(list))
                    return@runSuspendWithCatchError Either.Success(ItemListEntity(item = dbAfterInsert))
                } else {
                    val dbResult = itemListMapper.mapList(itemDAO.getItemByListId(list))
                    return@runSuspendWithCatchError Either.Success(ItemListEntity(item = dbResult))
                }
            } else {
                return@runSuspendWithCatchError Either.Success(ItemListEntity(item = listOf()))
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

}