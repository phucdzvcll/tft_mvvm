package com.tft_mvvm.app.features.details.model

data class ItemHolderViewHolder(
    val classOrOrigin: ClassOrOrigin,
    val listChamp: List<Champ>
) : ItemRv() {
    data class ClassOrOrigin(
        val classOrOriginName: String,
        val bonus: List<String>,
        val content: String
    )

    data class Champ(
        val id: String,
        val name: String,
        val imgUrl: String,
        val cost: String,
        val rank: String
    )
}