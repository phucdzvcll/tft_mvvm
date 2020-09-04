package com.tft_mvvm.domain.features.repository

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.model.ClassAndOriginListEntity
import com.tft_mvvm.domain.features.model.TeamBuilderListEntity

interface RepoRepository {
    suspend fun getAllChamps(
        isForceLoadData: Boolean
    ): Either<Failure, ChampListEntity>

    suspend fun getTeams(
        isForceLoadData: Boolean
    ): Either<Failure, TeamBuilderListEntity>

    suspend fun getClassAndOriginContent(
        listClassOrOriginName: List<String>
    ): Either<Failure, ClassAndOriginListEntity>

    suspend fun getChampById(
        id: String
    ): Either<Failure, ChampListEntity.Champ>

    suspend fun getAllClassAndOriginName(): Either<Failure, List<String>>

    suspend fun getTeamRecommendForChamp(
        id: String
    ): Either<Failure, TeamBuilderListEntity>
}
