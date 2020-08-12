package com.tft_mvvm.domain.features.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.repository.RepoRepository

class GetListChampsByOriginUseCase(private val repoRepository: RepoRepository):
    UseCase<GetListChampsByOriginUseCase.GetChampsByOriginUseCaseParam, Either<Failure, ChampListEntity>>(){
    override suspend fun executeInternal(params: GetChampsByOriginUseCaseParam): Either<Failure, ChampListEntity> {
        return repoRepository.getChampsByOrigin(
            origin = params.origin
        )
    }
    data class GetChampsByOriginUseCaseParam(
        var origin:String
    ): UseCaseParams

}