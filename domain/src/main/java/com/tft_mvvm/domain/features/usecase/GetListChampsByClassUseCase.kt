package com.tft_mvvm.domain.features.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.repository.RepoRepository

class GetListChampsByClassUseCase(private val repoRepository: RepoRepository):
    UseCase<GetListChampsByClassUseCase.GetChampsByClassUseCaseParam, Either<Failure, ChampListEntity>>(){
    override suspend fun executeInternal(params: GetChampsByClassUseCaseParam): Either<Failure, ChampListEntity> {
        return repoRepository.getChampsByClass(
            classs = params.classs
        )
    }
    data class GetChampsByClassUseCaseParam(
        var classs:String
    ): UseCaseParams

}