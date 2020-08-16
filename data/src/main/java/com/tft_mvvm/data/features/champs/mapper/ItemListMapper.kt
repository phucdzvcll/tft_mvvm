package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.data.local.model.ItemListDBO
import com.tft_mvvm.domain.features.model.ChampListEntity

class ItemListMapper : Mapper<ItemListDBO.ItemDBO, ChampListEntity.Champ.Item>() {
    override fun map(input: ItemListDBO.ItemDBO): ChampListEntity.Champ.Item {
        return ChampListEntity.Champ.Item(
            itemId = input.itemId,
            itemAvatar = input.itemAvatar,
            itemName = input.nameItem
        )
    }
}