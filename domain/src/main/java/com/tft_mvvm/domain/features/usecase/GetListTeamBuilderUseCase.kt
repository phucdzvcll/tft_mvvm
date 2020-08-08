package com.tft_mvvm.domain.features.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.model.TeamBuilderListEntity
import com.tft_mvvm.domain.features.repository.RepoRepository

class GetListTeamBuilderUseCase(private val repoRepoRepository: RepoRepository) :
    UseCase<GetListTeamBuilderUseCase.GetTeamUseCaseParam, Either<Failure, TeamBuilderListEntity>>() {
    override suspend fun executeInternal(params: GetTeamUseCaseParam): Either<Failure, TeamBuilderListEntity> {
        return repoRepoRepository.getTeams(
            isForceLoadData = params.isForceLoadData
        )
    }

    data class GetTeamUseCaseParam(val isForceLoadData: Boolean) : UseCaseParams
}