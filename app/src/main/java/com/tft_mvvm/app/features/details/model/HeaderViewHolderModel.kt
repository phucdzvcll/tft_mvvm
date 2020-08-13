package com.tft_mvvm.app.features.details.model

data class HeaderViewHolderModel(
    val nameSkill: String,
    val activated: String,
    val linkAvatarSkill: String,
    val linkChampCover: String,
    val name: String,
    val cost: String,
    val listSuitableItem: List<Item>
) : ItemRv() {

    data class Item(
        val id: String,
        val itemName: String,
        val itemAvatar: String
    )
}