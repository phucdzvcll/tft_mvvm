package com.tft_mvvm.app.features.dialog_show_details_champ.model

data class ChampDialogModel(
    val name: String,
    val skillName: String,
    val activated: String,
    val linkChampCover: String,
    val linkSkillAvatar: String,
    val cost: String,
    val itemSuitable: List<Item>
){
    data class Item(
        val id: String,
        val itemName: String,
        val itemAvatar: String
    )
}