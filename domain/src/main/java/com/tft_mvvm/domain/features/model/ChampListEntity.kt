package com.tft_mvvm.domain.features.model

data class ChampListEntity (val champs:List<Champ>){
    data class Champ (
        val name : String,
        val linkImg:String,
        val cost : String,
        val origin:String,
        val classs:String,
        val id:String,
        val skillName:String,
        val linkSkillAvatar:String,
        val activated:String,
        val rankChamp:String,
        val suitableItem:String,
        val linkChampCover:String
    )
}