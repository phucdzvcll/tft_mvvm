package com.tft_mvvm.app.features.main.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.main.model.Champ
import com.tft_mvvm.domain.features.model.ChampListEntity

class ChampByGoldMapper : Mapper<ChampListEntity.Champ, Champ>() {
    override fun map(input: ChampListEntity.Champ): Champ {
        return Champ(
            imgUrl = input.linkImg,
            cost = input.cost,
            id = input.id,
            name = input.name,
            rank = input.rankChamp
        )
    }
}