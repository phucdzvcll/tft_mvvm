package com.tft_mvvm.domain.features.champs.usecase

import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.repository.RepoRepository
import java.awt.Stroke

class GetChampsUseCase(private val repoRepository: RepoRepository):
    UseCase<UseCaseParams.Empty,ChampListEntity>(){
    override suspend fun executeInternal(params: UseCaseParams.Empty): ChampListEntity {
        return repoRepository.getChamps(
        )
    }

}

