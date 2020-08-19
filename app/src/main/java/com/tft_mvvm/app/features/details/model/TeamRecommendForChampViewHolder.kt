package com.tft_mvvm.app.features.details.model

import com.tft_mvvm.app.model.ItemRv

data class TeamRecommendForChampViewHolder(
    val name: String,
    val listChamp: List<Champ>
) : ItemRv() {
    data class Champ(
        val id: String,
        val name: String,
        val imgUrl: String,
        val cost: String,
        val threeStar: Boolean,
        val itemSuitable: List<Item>
    ) : ItemRv() {
        data class Item(
            val id: String,
            val itemName: String,
            val itemAvatar: String
        )
    }
}