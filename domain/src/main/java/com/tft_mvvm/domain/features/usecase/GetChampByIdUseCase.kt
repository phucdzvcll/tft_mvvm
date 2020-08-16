package com.tft_mvvm.domain.features.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.repository.RepoRepository


class GetChampByIdUseCase(private val repository: RepoRepository) :
    UseCase<GetChampByIdUseCase.GetChampByIdUseCaseParam, Either<Failure, ChampListEntity.Champ>>() {
    data class GetChampByIdUseCaseParam(val id: String) : UseCaseParams

    override suspend fun executeInternal(params: GetChampByIdUseCaseParam): Either<Failure, ChampListEntity.Champ> {
        return repository.getChampById(
            id = params.id
        )
    }
}