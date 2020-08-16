package com.tft_mvvm.app.features.main.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.main.adapter.AdapterShowRecommendTeamBuilder
import com.tft_mvvm.domain.features.model.TeamBuilderListEntity

class TeamBuilderRecommendMapper(private val champOfTeamMapper: ChampOfTeamMapper) :
    Mapper<TeamBuilderListEntity.TeamsBuilder, AdapterShowRecommendTeamBuilder.TeamBuilder>() {
    override fun map(input: TeamBuilderListEntity.TeamsBuilder): AdapterShowRecommendTeamBuilder.TeamBuilder {
        return AdapterShowRecommendTeamBuilder.TeamBuilder(
            name = input.name,
            listChamp = champOfTeamMapper.mapList(input.champs.champs)
        )
    }


}