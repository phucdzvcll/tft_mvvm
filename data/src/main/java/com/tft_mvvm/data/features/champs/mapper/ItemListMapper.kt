package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.data.local.model.ItemListDBO
import com.tft_mvvm.domain.features.model.ItemListEntity

class ItemListMapper : Mapper<ItemListDBO.ItemDBO, ItemListEntity.Item>() {
    override fun map(input: ItemListDBO.ItemDBO): ItemListEntity.Item {
        return ItemListEntity.Item(
            itemId = input.itemId,
            itemAvatar = input.itemAvatar,
            itemName = input.nameItem
        )
    }
}