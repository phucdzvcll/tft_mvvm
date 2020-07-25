package com.example.presentation.mapper

import com.example.common_jvm.mapper.Mapper
import com.example.presentation.features.champs.model.model.Champ
import com.tft_mvvm.domain.features.champs.model.ChampListEntity

class ChampMapper : Mapper<ChampListEntity.Champ, Champ>() {
    override fun map(input: ChampListEntity.Champ): Champ {

        return Champ(
            name = input.name,
            linkimg = input.linkimg,
            coat = input.coat
        )
    }
}