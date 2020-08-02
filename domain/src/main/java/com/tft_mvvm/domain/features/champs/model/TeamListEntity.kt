package com.tft_mvvm.domain.features.champs.model

data class TeamListEntity (val teams:List<Team>){
    data class Team(
        val name:String,
        val listId:String
    )

}