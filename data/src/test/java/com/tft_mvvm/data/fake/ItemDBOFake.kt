package com.tft_mvvm.data.fake

import com.tft_mvvm.data.local.model.ItemListDBO

object ItemDBOFake {

    fun provideListItemResponse(number: Int) = mutableListOf<ItemListDBO.ItemDBO>().apply {
        repeat(number) { index -> add(ItemDBOFake.provideItemDBO(index)) }
    }.toList()

    fun provideItemDBO(index: Int = 1) = ItemListDBO.ItemDBO(
        nameItem = "itemName$index",
        itemId = "item$index",
        itemAvatar = "itemAvatar$index"
    )
}