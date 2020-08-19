package com.tft_mvvm.app.features.details.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.details.model.ChampDetailsModel
import com.tft_mvvm.domain.features.model.ChampListEntity

class ItemMapper : Mapper<ChampListEntity.Champ.Item, ChampDetailsModel.Item>() {
    override fun map(input: ChampListEntity.Champ.Item): ChampDetailsModel.Item {
        return ChampDetailsModel.Item(
            id = input.itemId,
            itemAvatar = input.itemAvatar,
            itemName = input.itemName
        )
    }
}