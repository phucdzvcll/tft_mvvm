package com.tft_mvvm.domain.features.repository

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.model.ClassAndOriginListEntity
import com.tft_mvvm.domain.features.model.ItemListEntity
import com.tft_mvvm.domain.features.model.TeamBuilderListEntity

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
        isForceLoadData: Boolean
    ): Either<Failure, TeamBuilderListEntity>

    suspend fun getClassAndOriginContent(
        isForceLoadData: Boolean,
        classOrOriginName: String
    ): Either<Failure, ClassAndOriginListEntity.ClassAndOrigin>

    suspend fun getListSuitableItem(
        isForceLoadData: Boolean,
        listId: String
    ): Either<Failure, ItemListEntity>
}
