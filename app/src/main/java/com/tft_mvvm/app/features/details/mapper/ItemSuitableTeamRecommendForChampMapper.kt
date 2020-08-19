package com.tft_mvvm.app.features.details.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.details.model.TeamRecommendForChamp
import com.tft_mvvm.domain.features.model.ChampListEntity

class ItemSuitableTeamRecommendForChampMapper :
    Mapper<ChampListEntity.Champ.Item, TeamRecommendForChamp.Champ.Item>() {
    override fun map(input: ChampListEntity.Champ.Item): TeamRecommendForChamp.Champ.Item {
        return TeamRecommendForChamp.Champ.Item(
            id = input.itemId,
            itemAvatar = input.itemAvatar,
            itemName = input.itemName
        )
    }
}
