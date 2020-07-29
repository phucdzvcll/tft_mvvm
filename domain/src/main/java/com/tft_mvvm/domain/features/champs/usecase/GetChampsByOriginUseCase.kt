package com.tft_mvvm.domain.features.champs.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.repository.RepoRepository

class GetChampsByOriginUseCase(private val repoRepository: RepoRepository):
    UseCase<GetChampsByOriginUseCase.GetChampsByOriginUseCaseParam, Either<Failure,ChampListEntity>>(){
    override suspend fun executeInternal(params: GetChampsByOriginUseCaseParam): Either<Failure,ChampListEntity> {
        return repoRepository.getChampsByOrigin(
            origin = params.origin
        )
    }
    data class GetChampsByOriginUseCaseParam(
        var origin:String
    ): UseCaseParams

}