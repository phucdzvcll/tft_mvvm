package com.tft_mvvm.app.features.details.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.details.model.ChampDetailsModel
import com.tft_mvvm.domain.features.model.ChampListEntity

class ChampOfChampDetailsMapper(private val itemMapper: ItemMapper) :
    Mapper<ChampListEntity.Champ, ChampDetailsModel.Champ>() {
    override fun map(input: ChampListEntity.Champ): ChampDetailsModel.Champ {
        return ChampDetailsModel.Champ(
            id = input.id,
            itemSuitable = itemMapper.mapList(input.suitableItem),
            imgUrl = input.linkImg,
            cost = input.cost,
            threeStar = input.threeStar,
            name = input.name
        )
    }
}