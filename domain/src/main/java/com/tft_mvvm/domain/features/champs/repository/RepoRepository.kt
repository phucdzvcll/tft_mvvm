package com.tft_mvvm.domain.features.champs.repository

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.model.TeamBuilderListEntity
import com.tft_mvvm.domain.features.champs.model.TeamListEntity

interface RepoRepository {
    suspend fun getChamps(
    ): Either<Failure, ChampListEntity>

    suspend fun getChampsByOrigin(
        origin: String
    ): Either<Failure, ChampListEntity>

    suspend fun getChampsByClass(
        classs: String
    ): Either<Failure, ChampListEntity>

    suspend fun getTeams(
    ): Either<Failure, TeamBuilderListEntity>

}