package com.tft_mvvm.domain.features.champs.model

data class ChampListEntity (val champs:List<Champ>){
    data class Champ (
        val name : String,
        val linkimg:String,
        val coat : String
    )
}