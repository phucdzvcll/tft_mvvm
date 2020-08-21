package com.tft_mvvm.data.fake

import com.tft_mvvm.domain.features.model.ChampListEntity

object ItemEntityFake {

    fun provideListItemResponse(number: Int) = mutableListOf<ChampListEntity.Champ.Item>().apply {
        repeat(number) { index -> add(ItemEntityFake.provideItemEntity(index)) }
    }.toList()

    fun provideItemEntity(index: Int = 1) = ChampListEntity.Champ.Item(
        itemName = "itemName$index",
        itemId = "item$index",
        itemAvatar = "itemAvatar$index"
    )
}