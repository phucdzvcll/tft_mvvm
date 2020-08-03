package com.tft_mvvm.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tft_mvvm.data.features.champs.model.Champ
import java.lang.StringBuilder

class TeamListDBO(val teamDBOs:List<TeamDBO>){

    @Entity(tableName = "team")
    data class TeamDBO(
        val name :String,
        @PrimaryKey
        val id:String,
        val listId : String
    )
}
