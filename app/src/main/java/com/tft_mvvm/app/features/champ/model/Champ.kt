package com.tft_mvvm.app.features.champ.model

import java.io.Serializable

data class Champ (
    val name : String,
    val linkImg: String,
    val cost : String,
    val origin:String,
    val classs:String,
    val id :String,
    val skillName:String,
    val linkSkilAvatar:String,
    val activated:String,
    val rankChamp:String,
    val linkChampCover:String
):Serializable