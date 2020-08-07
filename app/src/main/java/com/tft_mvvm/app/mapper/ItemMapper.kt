package com.tft_mvvm.app.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.champ.model.Item
import com.tft_mvvm.domain.features.champs.model.ItemListEntity

class ItemMapper : Mapper<ItemListEntity.Item, Item>() {
    override fun map(input: ItemListEntity.Item): Item {
        return Item(
            itemName = input.itemName,
            itemAvatar = input.itemAvatar,
            itemId = input.itemId
        )
    }
}