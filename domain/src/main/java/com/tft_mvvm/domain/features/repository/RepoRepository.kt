package com.tft_mvvm.domain.features.repository

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.model.ClassAndOriginListEntity
import com.tft_mvvm.domain.features.model.TeamBuilderListEntity

interface RepoRepository {
    suspend fun getChamps(
        isForceLoadData: Boolean
    ): Either<Failure, ChampListEntity>

    suspend fun getChampsByOrigin(
        origin: String
    ): Either<Failure, ChampListEntity>

    suspend fun getChampsByClass(
        classs: String
    ): Either<Failure, ChampListEntity>

    suspend fun getTeams(
        isForceLoadData: Boolean
    ): Either<Failure, TeamBuilderListEntity>

    suspend fun getClassAndOriginContent(
        isForceLoadData: Boolean,
        classOrOriginName: String
    ): Either<Failure, ClassAndOriginListEntity.ClassAndOrigin>

    suspend fun updateChamp(
        id: String
    ): Either<Failure, ChampListEntity.Champ>

    suspend fun getChampById(
        id: String
    ): Either<Failure, ChampListEntity.Champ>
}
