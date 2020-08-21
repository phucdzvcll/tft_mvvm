package com.tft_mvvm.data.fake

import com.tft_mvvm.data.features.champs.model.GsxNameItem
import com.tft_mvvm.data.features.champs.model.Item

object ItemResponseFake {

    fun provideListItemResponse(number: Int) = mutableListOf<Item>().apply {
        repeat(number) { index -> add(provideItemResponse(index)) }
    }.toList()

    fun provideItemResponse(index: Int = 1) = Item(
        itemAvatar = GsxNameItem("itemAvatar$index"),
        itemName = GsxNameItem("itemName$index"),
        itemId = GsxNameItem("item$index")
    )
}