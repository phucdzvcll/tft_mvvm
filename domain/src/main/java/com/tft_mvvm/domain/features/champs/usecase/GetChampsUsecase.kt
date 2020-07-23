package com.tft_mvvm.domain.features.champs.usecase

import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.repository.ChampRepository

class GetChampsUseCase(private val champRepository: ChampRepository):
    UseCase<GetChampsUseCaseParams,ChampListEntity>(){
    override suspend fun executeInternal(params: GetChampsUseCaseParams): ChampListEntity {
        return champRepository.getChamps(
            tags = params.tags,
            type = params.type,
            limitAmount = params.limitAmount

        )
    }

}

data class GetChampsUseCaseParams(
    val tags: List<String>,
    val type: String,
    val limitAmount: Double
) : UseCaseParams