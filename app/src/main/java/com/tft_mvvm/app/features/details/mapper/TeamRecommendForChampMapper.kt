package com.tft_mvvm.app.features.details.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.details.model.TeamRecommendForChamp
import com.tft_mvvm.domain.features.model.TeamBuilderListEntity

class TeamRecommendForChampMapper(private val champOfTeamRecommendForChampMapper: ChampOfTeamRecommendForChampMapper) :
    Mapper<TeamBuilderListEntity.TeamsBuilder, TeamRecommendForChamp>() {
    override fun map(input: TeamBuilderListEntity.TeamsBuilder): TeamRecommendForChamp {
        return TeamRecommendForChamp(
            name = input.name,
            listChamp = champOfTeamRecommendForChampMapper.mapList(input.champs.champs)
        )
    }
}