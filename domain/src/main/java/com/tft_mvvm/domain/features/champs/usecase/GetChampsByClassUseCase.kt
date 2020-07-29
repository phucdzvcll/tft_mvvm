package com.tft_mvvm.domain.features.champs.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.repository.RepoRepository

class GetChampsByClassUseCase(private val repoRepository: RepoRepository):
    UseCase<GetChampsByClassUseCase.GetChampsByClassUseCaseParam, Either<Failure,ChampListEntity>>(){
    override suspend fun executeInternal(params: GetChampsByClassUseCaseParam): Either<Failure, ChampListEntity> {
        return repoRepository.getChampsByClass(
            classs = params.classs
        )
    }
    data class GetChampsByClassUseCaseParam(
        var classs:String
    ): UseCaseParams

}