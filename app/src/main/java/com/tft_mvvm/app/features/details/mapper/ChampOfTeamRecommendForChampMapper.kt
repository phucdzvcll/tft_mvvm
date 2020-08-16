package com.tft_mvvm.app.features.details.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.details.model.TeamRecommendForChamp
import com.tft_mvvm.domain.features.model.ChampListEntity

class ChampOfTeamRecommendForChampMapper(private val itemSuitableTeamRecommendForChampMapper: ItemSuitableTeamRecommendForChampMapper) :
    Mapper<ChampListEntity.Champ, TeamRecommendForChamp.Champ>() {
    override fun map(input: ChampListEntity.Champ): TeamRecommendForChamp.Champ {
        return TeamRecommendForChamp.Champ(
            id = input.id,
            itemSuitable = itemSuitableTeamRecommendForChampMapper.mapList(input.suitableItem),
            imgUrl = input.linkImg,
            cost = input.cost,
            name = input.name
        )
    }
}