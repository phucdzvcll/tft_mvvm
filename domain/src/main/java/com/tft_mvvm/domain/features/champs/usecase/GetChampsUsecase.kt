package com.tft_mvvm.domain.features.champs.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.repository.RepoRepository

class GetChampsUseCase(private val repoRepository: RepoRepository) :
    UseCase<UseCaseParams.Empty, Either<Failure,ChampListEntity>>() {
    override suspend fun executeInternal(params: UseCaseParams.Empty): Either<Failure,ChampListEntity> {
        return repoRepository.getChamps()
    }

}

