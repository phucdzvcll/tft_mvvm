package com.tft_mvvm.app.features.main.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.main.adapter.AdapterShowChampInTeamBuilder
import com.tft_mvvm.domain.features.model.ChampListEntity

class ItemOfTeamMapper : Mapper<ChampListEntity.Champ.Item, AdapterShowChampInTeamBuilder.Champ.Item>() {
    override fun map(input: ChampListEntity.Champ.Item): AdapterShowChampInTeamBuilder.Champ.Item {
        return AdapterShowChampInTeamBuilder.Champ.Item(
            id = input.itemId,
            itemAvatar = input.itemAvatar,
            itemName = input.itemName
        )
    }
}