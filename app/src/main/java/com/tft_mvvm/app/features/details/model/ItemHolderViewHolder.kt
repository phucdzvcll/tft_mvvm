package com.tft_mvvm.app.features.details.model

import com.tft_mvvm.app.model.Champ

data class ItemHolderViewHolder(
    val classOrOrigin: ClassOrOrigin,
    val listChamp: List<Champ>
) : ItemRv() {
    data class ClassOrOrigin(
        val classOrOriginName: String,
        val bonus: List<String>,
        val content: String
    )

}