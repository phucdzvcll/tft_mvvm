package com.tft_mvvm.app.features.fake

import com.tft_mvvm.domain.features.model.ChampListEntity

object ItemEntityFake {

    fun provideListItemEntity(number: Int) = mutableListOf<ChampListEntity.Champ.Item>().apply {
        repeat(number) { index -> add(
            provideItemEntity(
                index
            )
        ) }
    }.toList()

    fun provideItemEntity(index: Int = 1) = ChampListEntity.Champ.Item(
        itemName = "itemName$index",
        itemId = "item$index",
        itemAvatar = "itemAvatar$index"
    )
}