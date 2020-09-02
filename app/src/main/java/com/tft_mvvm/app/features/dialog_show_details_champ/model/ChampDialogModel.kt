package com.tft_mvvm.app.features.dialog_show_details_champ.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ChampDialogModel(
    val name: String,
    val skillName: String,
    val activated: String,
    val linkChampCover: String,
    val linkSkillAvatar: String,
    val cost: String,
    val itemSuitable: List<Item>,
    val star: String
) {
    @Parcelize
    data class Item(
        val id: String,
        val itemName: String,
        val itemAvatar: String
    ) : Parcelable
}