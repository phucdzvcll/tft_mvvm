package com.tft_mvvm.domain.features.champs.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.model.TeamBuilderListEntity
import com.tft_mvvm.domain.features.champs.repository.RepoRepository

class GetTeamUseCase(private val repoRepoRepository: RepoRepository) :
    UseCase<UseCaseParams.Empty, Either<Failure, TeamBuilderListEntity>>() {
    override suspend fun executeInternal(params: UseCaseParams.Empty): Either<Failure, TeamBuilderListEntity> {
        return repoRepoRepository.getTeams()
    }
}