package com.tft_mvvm.domain.features.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.repository.RepoRepository

class GetChampsUseCase(private val repoRepository: RepoRepository) :
    UseCase<UseCaseParams.Empty, Either<Failure, ChampListEntity>>() {
    override suspend fun executeInternal(params: UseCaseParams.Empty): Either<Failure, ChampListEntity> {
        return repoRepository.getChamps()
    }

}

