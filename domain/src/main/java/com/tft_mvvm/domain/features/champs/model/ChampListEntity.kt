package com.tft_mvvm.domain.features.champs.model

data class ChampListEntity (val champs:List<Champ>){
    data class Champ (
        val name : String,
        val linkImg:String,
        val coat : String,
        val origin:String,
        val classs:String,
        val id:String,
        val skillName:String,
        val linkSkillAvatar:String,
        val activated:String,
        val linkChampCover:String
    )
}