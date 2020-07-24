package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.mapper.Mapper
import com.example.common_jvm.nullable.defaultEmpty
import com.tft_mvvm.data.features.champs.model.ChampListResponse
import com.tft_mvvm.domain.features.champs.model.ChampListEntity

class ChampListMapper : Mapper<ChampListResponse?, ChampListEntity>() {
    override fun map(input: ChampListResponse?): ChampListEntity {
        val champ = input?.feed?.champs?.filterNotNull().defaultEmpty().map { champ ->
            //todo need update id vs date correct
            ChampListEntity.Champ(
                name = champ.name.value,
                linkimg = champ.linkimg.value,
                coat = champ.coat.value
            )
        }
        return ChampListEntity(champs = champ)
    }
}