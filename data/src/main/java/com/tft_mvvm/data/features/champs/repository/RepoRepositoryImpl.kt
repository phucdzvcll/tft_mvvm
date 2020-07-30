package com.tft_mvvm.data.features.champs.repository

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.example.common_jvm.function.Either.Companion.runSuspendWithCatchError
import com.tft_mvvm.data.exception_interceptor.RemoteExceptionInterceptor
import com.tft_mvvm.data.features.champs.mapper.ChampDaoEntityMapper
import com.tft_mvvm.data.features.champs.mapper.ChampListMapper
import com.tft_mvvm.data.features.champs.service.ApiService
import com.tft_mvvm.data.local.ChampDAO
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.repository.RepoRepository

class RepoRepositoryImpl(
    private val apiService: ApiService,
    private val champDAO: ChampDAO,
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

    override suspend fun getChampsByOrigin(origin: String): Either<Failure, ChampListEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getChampsByClass(classs: String): Either<Failure, ChampListEntity> {
        TODO("Not yet implemented")
    }


}