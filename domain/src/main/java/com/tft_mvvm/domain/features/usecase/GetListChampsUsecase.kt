package com.tft_mvvm.domain.features.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.repository.RepoRepository

class GetListChampsUseCase(private val repoRepository: RepoRepository) :
    UseCase<GetListChampsUseCase.GetAllChampUseCaseParam, Either<Failure, ChampListEntity>>() {
    override suspend fun executeInternal(params: GetAllChampUseCaseParam): Either<Failure, ChampListEntity> {
        return repoRepository.getChamps(
            isForceLoadData = params.isForceLoadData
        )
    }
    data class GetAllChampUseCaseParam(
        val isForceLoadData: Boolean
    ):UseCaseParams
}

