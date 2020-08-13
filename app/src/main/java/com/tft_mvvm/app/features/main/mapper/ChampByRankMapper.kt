package com.tft_mvvm.app.features.main.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.main.adapter.AdapterShowChampByRank
import com.tft_mvvm.domain.features.model.ChampListEntity

class ChampByRankMapper : Mapper<ChampListEntity.Champ, AdapterShowChampByRank.ChampByRank>() {
    override fun map(input: ChampListEntity.Champ): AdapterShowChampByRank.ChampByRank {
        return AdapterShowChampByRank.ChampByRank(
            id = input.id,
            name = input.name,
            cost = input.cost,
            imgUrl = input.linkImg,
            rank = input.rankChamp
        )
    }
}