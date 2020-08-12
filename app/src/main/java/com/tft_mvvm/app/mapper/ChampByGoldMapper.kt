package com.tft_mvvm.app.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.ui.adapter.AdapterShowByGold
import com.tft_mvvm.domain.features.model.ChampListEntity

class ChampByGoldMapper : Mapper<ChampListEntity.Champ, AdapterShowByGold.ItemViewHolder.ChampByGold>() {
    override fun map(input: ChampListEntity.Champ): AdapterShowByGold.ItemViewHolder.ChampByGold {
        return AdapterShowByGold.ItemViewHolder.ChampByGold(
            name = input.name,
            linkImg = input.linkImg,
            id = input.id,
            cost = input.cost
        )
    }

}