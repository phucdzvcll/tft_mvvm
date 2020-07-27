package com.tft_mvvm.domain.features.champs.usecase

import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.repository.RepoRepository

class GetChampsByOriginUseCase(private val repoRepository: RepoRepository):
    UseCase<GetChampsByOriginUseCase.GetChampsByOriginUseCaseParam, ChampListEntity>(){
    override suspend fun executeInternal(params: GetChampsByOriginUseCaseParam): ChampListEntity {
        return repoRepository.getChampsByOrigin(
            origin = params.origin
        )
    }
    data class GetChampsByOriginUseCaseParam(
        var origin:String
    ): UseCaseParams

}