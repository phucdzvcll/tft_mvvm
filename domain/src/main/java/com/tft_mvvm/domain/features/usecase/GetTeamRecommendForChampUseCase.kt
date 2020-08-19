package com.tft_mvvm.domain.features.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.model.TeamBuilderListEntity
import com.tft_mvvm.domain.features.repository.RepoRepository

class GetTeamRecommendForChampUseCase(private val repoRepository: RepoRepository) :
    UseCase<GetTeamRecommendForChampUseCase.GetTeamRecommendForChampUseCaseParam, Either<Failure, TeamBuilderListEntity>>() {
    override suspend fun executeInternal(params: GetTeamRecommendForChampUseCaseParam): Either<Failure, TeamBuilderListEntity> {
        return repoRepository.getTeamRecommendForChamp(
            id = params.id
        )
    }

    data class GetTeamRecommendForChampUseCaseParam(
        val id: String
    ) : UseCaseParams
}
