package com.tft_mvvm.domain.features.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.model.ItemListEntity
import com.tft_mvvm.domain.features.repository.RepoRepository

class GetListSuitableItemsUseCase(private val repoRepository: RepoRepository) :
    UseCase<GetListSuitableItemsUseCase.GetListSuitableItemUseCaseParam, Either<Failure, ItemListEntity>>() {
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