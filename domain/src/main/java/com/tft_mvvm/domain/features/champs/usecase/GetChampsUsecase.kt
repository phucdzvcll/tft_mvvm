package com.tft_mvvm.domain.features.champs.usecase

import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.repository.ChampRepository
import java.util.*

class GetChampsUseCase(private val champRepository: ChampRepository):
    UseCase<GetChampsUseCase.GetChampsUseCaseParam,ChampListEntity>(){
    override suspend fun executeInternal(params: GetChampsUseCaseParam): ChampListEntity {
        return champRepository.getChamps(
            name = params.name,
            linkimg = params.linimg,
            coat = params.coat
        )
    }
data class GetChampsUseCaseParam(
    var name:String,
    var linimg:String,
    var coat:String
):UseCaseParams
}

