package com.tft_mvvm.app.features.main.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.model.Champ
import com.tft_mvvm.domain.features.model.ChampListEntity

class ChampMapper :
    Mapper<ChampListEntity.Champ, Champ>() {
    override fun map(input: ChampListEntity.Champ): Champ{
        return Champ(
            id = input.id,
            name = input.name,
            cost = input.cost,
            rank = input.rankChamp,
            imgUrl = input.linkImg
        )
    }
}