package com.tft_mvvm.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ChampListDBO(val champDBOs:List<ChampDBO>){

    @Entity(tableName = "champ")
    data class ChampDBO(
        @PrimaryKey
        val id :String,
        val name : String,
        val linkImg: String,
        val cost : String,
        val origin:String,
        val classs:String,
        val skillName:String,
        val linkSkilAvatar:String,
        val activated:String,
        val rankChamp:String,
        val suitableItem:String,
        val linkChampCover:String
    )
}