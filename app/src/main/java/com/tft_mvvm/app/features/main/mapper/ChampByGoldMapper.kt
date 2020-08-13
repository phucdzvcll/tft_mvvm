package com.tft_mvvm.app.features.main.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.main.adapter.AdapterShowByGold
import com.tft_mvvm.domain.features.model.ChampListEntity

class ChampByGoldMapper :
    Mapper<ChampListEntity.Champ, AdapterShowByGold.ItemViewHolder.ChampByGold>() {
    override fun map(input: ChampListEntity.Champ): AdapterShowByGold.ItemViewHolder.ChampByGold {
        return AdapterShowByGold.ItemViewHolder.ChampByGold(
            id = input.id,
            name = input.name,
            cost = input.cost,
            linkImg = input.linkImg
        )
    }
}