package com.tft_mvvm.domain.features.model

data class ClassAndOriginListEntity(val listClassAndOrigin: List<ClassAndOrigin>) {
    data class ClassAndOrigin(
        val classOrOriginName: String,
        val bonus: String,
        val champEntity : ChampListEntity,
        val imgUrl:String,
        val content: String
    )
}
