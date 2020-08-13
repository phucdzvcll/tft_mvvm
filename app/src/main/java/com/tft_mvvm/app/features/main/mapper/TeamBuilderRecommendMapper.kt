package com.tft_mvvm.app.features.main.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.main.adapter.AdapterShowRecommendTeamBuilder
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.model.TeamBuilderListEntity

class TeamBuilderRecommendMapper() :
    Mapper<TeamBuilderListEntity.TeamsBuilder, AdapterShowRecommendTeamBuilder.TeamBuilder>() {
    override fun map(input: TeamBuilderListEntity.TeamsBuilder): AdapterShowRecommendTeamBuilder.TeamBuilder {
        return AdapterShowRecommendTeamBuilder.TeamBuilder(
            name = input.name,
            listChamp = input.champs.champs.map { mapChampByRank(it) }
        )
    }

    private fun mapChampByRank(input: ChampListEntity.Champ): AdapterShowRecommendTeamBuilder.ChampByRank {
        return AdapterShowRecommendTeamBuilder.ChampByRank(
            name = input.name,
            rank = input.rankChamp,
            imgUrl = input.linkImg,
            cost = input.cost,
            id = input.id
        )
    }
}