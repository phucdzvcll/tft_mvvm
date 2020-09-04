package com.tft_mvvm.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ClassAndOriginListDBO(val classAndOrigins: List<ClassAndOrigin>) {
    @Entity(tableName = "classAndOrigin")
    data class ClassAndOrigin(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        val classOrOriginName: String,
        val bonus:String,
        val imgUrl:String,
        val content: String
    )
}