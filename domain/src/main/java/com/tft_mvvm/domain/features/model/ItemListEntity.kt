package com.tft_mvvm.domain.features.model

data class ItemListEntity(val iteam: List<Item>) {
    data class Item(
        val itemId: String,
        val itemName: String,
        val itemAvatar: String
    )
}
