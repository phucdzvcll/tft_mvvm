package com.tft_mvvm.domain.features.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.repository.RepoRepository

class UpdateChampUseCase(private val repoRepository: RepoRepository) :
    UseCase<UpdateChampUseCase.UpdateChampUseCaseParam, Either<Failure, ChampListEntity.Champ>>() {
    override suspend fun executeInternal(params: UpdateChampUseCaseParam): Either<Failure, ChampListEntity.Champ> {
        return repoRepository.updateChamp(
            id = params.id
        )
    }
    data class UpdateChampUseCaseParam(
        val id: String
    ) : UseCaseParams
}
