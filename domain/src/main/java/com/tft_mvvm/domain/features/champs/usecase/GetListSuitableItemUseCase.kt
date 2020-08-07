package com.tft_mvvm.domain.features.champs.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.sun.net.httpserver.Authenticator
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.model.ItemListEntity
import com.tft_mvvm.domain.features.champs.repository.RepoRepository

class GetListSuitableItemUseCase(private val repoRepository: RepoRepository) :
    UseCase<GetListSuitableItemUseCase.GetListSuitableItemUseCaseParam, Either<Failure, ItemListEntity>>() {
    data class GetListSuitableItemUseCaseParam(
        val isForceLoadData:Boolean,
        val listId:String
    ) : UseCaseParams

    override suspend fun executeInternal(params: GetListSuitableItemUseCaseParam): Either<Failure, ItemListEntity> {
        return repoRepository.getListSuitableItem(
            isForceLoadData = params.isForceLoadData,
            listId = params.listId
        )
    }
}