package com.tft_mvvm.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ItemListDBO(val items: List<ItemDBO>) {
    @Entity(tableName = "item")
    data class ItemDBO(
        @PrimaryKey
        val itemId: String,
        val nameItem: String,
        val itemAvatar: String
    )
}