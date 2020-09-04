package com.tft_mvvm.app.features.main.model

data class ClassAndOriginContent(
    val classOrOriginName: String,
    val listChamp: List<Champ>,
    val imgUrl: String
){
    data class Champ(
        val imgUrl:String,
        val cost:String,
        val id:String,
        val classAndOriginName:List<String>
    )
}