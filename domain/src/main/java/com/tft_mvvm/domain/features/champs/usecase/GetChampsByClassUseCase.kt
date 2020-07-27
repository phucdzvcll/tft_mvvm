package com.tft_mvvm.domain.features.champs.usecase

import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.repository.RepoRepository

class GetChampsByClassUseCase(private val repoRepository: RepoRepository):
    UseCase<GetChampsByClassUseCase.GetChampsByClassUseCaseParam, ChampListEntity>(){
    override suspend fun executeInternal(params: GetChampsByClassUseCaseParam): ChampListEntity {
        return repoRepository.getChampsByClass(
            classs = params.classs
        )

    }
    data class GetChampsByClassUseCaseParam(
        var classs:String
    ): UseCaseParams

}