package com.tft_mvvm.data.features.champs.repository.fake

import com.tft_mvvm.data.local.model.ItemListDBO

object ItemDBOFake {

    fun provideListItemDBO(number: Int) = mutableListOf<ItemListDBO.ItemDBO>().apply {
        repeat(number) { index -> add(
            provideItemDBO(
                index
            )
        ) }
    }.toList()

    fun provideItemDBO(index: Int = 1) = ItemListDBO.ItemDBO(
        nameItem = "itemName$index",
        itemId = "item$index",
        itemAvatar = "itemAvatar$index"
    )
}