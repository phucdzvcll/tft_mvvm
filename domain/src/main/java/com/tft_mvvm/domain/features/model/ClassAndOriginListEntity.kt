package com.tft_mvvm.domain.features.model

data class ClassAndOriginListEntity(val listClassAndOrigin: List<ClassAndOrigin>) {
    data class ClassAndOrigin(
        val classOrOriginName: String,
        val bonus: String,
        val listChamp : ChampListEntity,
        val content: String
    )
}
