package com.tft_mvvm.domain.features.champs.repository

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.model.ClassAndOriginListEntity
import com.tft_mvvm.domain.features.champs.model.TeamBuilderListEntity
import com.tft_mvvm.domain.features.champs.model.TeamListEntity
import java.awt.Stroke

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
        isForceLoadData:Boolean
    ): Either<Failure, TeamBuilderListEntity>

    suspend fun getClassAndOriginContent(
        classOrOriginName:String
    ):Either<Failure, ClassAndOriginListEntity>
}
