package com.tft_mvvm.domain.features.champs.model

data class ClassAndOriginListEntity(val classAndOrigin: List<ClassAndOrigin>) {
    data class ClassAndOrigin(
        val classOrOriginName: String,
        val bonus:String,
        val content: String
    )
}
