package com.tft_mvvm.app.features.champ.model

import java.io.Serializable

data class Champ (
    var name : String,
    var linkImg: String,
    var coat : String,
    var origin:String,
    var classs:String,
    var id :String,
    var skillName:String,
    var linkSkilAvatar:String,
    var activated:String,
    var linkChampCover:String
):Serializable