package com.tft_mvvm.domain.features.champs.usecase

import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.repository.RepoRepository
import java.awt.Stroke

class GetChampsUseCase(private val repoRepository: RepoRepository):
    UseCase<GetChampsUseCase.GetChampsUseCaseParam,ChampListEntity>(){
    override suspend fun executeInternal(params: GetChampsUseCaseParam): ChampListEntity {
        return repoRepository.getChamps(
            name = params.name,
            linkImg = params.linkImg,
            coat = params.coat,
            classs = params.classs,
            origin = params.origin,
            id = params.id,
            activated = params.activated,
            linkSkillAvatar = params.linkSkillAvatar,
            skillName = params.skillName
        )
    }
data class GetChampsUseCaseParam(
    var name:String,
    var linkImg:String,
    var coat:String,
    var origin:String,
    var classs:String,
    var id :String,
    var skillName:String,
    var linkSkillAvatar:String,
    var activated:String
):UseCaseParams
}

