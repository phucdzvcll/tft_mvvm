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
        val coat : String,
        val origin:String,
        val classs:String,
        val skillName:String,
        val linkSkilAvatar:String,
        val activated:String,
        val linkChampCover:String
    )
}