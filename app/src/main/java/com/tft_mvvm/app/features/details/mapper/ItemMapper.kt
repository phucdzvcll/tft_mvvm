package com.tft_mvvm.app.features.details.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.details.model.HeaderViewHolderModel
import com.tft_mvvm.domain.features.model.ItemListEntity

class ItemMapper : Mapper<ItemListEntity.Item, HeaderViewHolderModel.Item>() {
    override fun map(input: ItemListEntity.Item): HeaderViewHolderModel.Item {
        return HeaderViewHolderModel.Item(
            id = input.itemId,
            itemAvatar = input.itemAvatar,
            itemName = input.itemName
        )
    }
}