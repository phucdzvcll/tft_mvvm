package com.tft_mvvm.data.features.champs.repository

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.example.common_jvm.function.Either.Companion.runSuspendWithCatchError
import com.tft_mvvm.data.exception_interceptor.RemoteExceptionInterceptor
import com.tft_mvvm.data.features.champs.mapper.ChampDaoEntityMapper
import com.tft_mvvm.data.features.champs.mapper.ChampListMapper
import com.tft_mvvm.data.features.champs.mapper.TeamDaoEntityMapper
import com.tft_mvvm.data.features.champs.mapper.TeamListMapper
import com.tft_mvvm.data.features.champs.model.Team
import com.tft_mvvm.data.features.champs.service.ApiService
import com.tft_mvvm.data.local.database.ChampDAO
import com.tft_mvvm.data.local.database.TeamDAO
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.model.TeamBuilderListEntity
import com.tft_mvvm.domain.features.champs.model.TeamListEntity
import com.tft_mvvm.domain.features.champs.repository.RepoRepository

class RepoRepositoryImpl(
    private val apiService: ApiService,
    private val champDAO: ChampDAO,
    private val teamDAO: TeamDAO,
    private val teamListMapper: TeamListMapper,
    private val teamDaoEntityMapper: TeamDaoEntityMapper,
    private val remoteExceptionInterceptor: RemoteExceptionInterceptor,
    private val champListMapper: ChampListMapper,
    private val champDaoEntityMapper: ChampDaoEntityMapper

) : RepoRepository {
    override suspend fun getChamps() =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            val dbResult = ChampListEntity(champs = champListMapper.mapList(champDAO.getData()))
            if (dbResult.champs.isNullOrEmpty()) {
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

    override suspend fun getTeams() =
        runSuspendWithCatchError(listOf(remoteExceptionInterceptor)) {
            var dbTeamListEntity = TeamListEntity(teams = teamListMapper.mapList(teamDAO.getAllTeam()))
            if (teamDAO.getAllTeam().isEmpty()) {
                val teamListResponse = apiService.getTeamList()
                val teamListDBO = teamDaoEntityMapper.map(teamListResponse)
                teamDAO.insertTeam(teamListDBO.teamDBOs)
                dbTeamListEntity = TeamListEntity(teams = teamListMapper.mapList(teamDAO.getAllTeam()))
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
            return@runSuspendWithCatchError Either.Success(TeamBuilderListEntity(listTeamBuilder))

        }

}