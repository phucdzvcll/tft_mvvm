package com.tft_mvvm.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tft_mvvm.data.features.champs.model.Champ

class TeamListDBO(val teamDBOs:List<TeamDBO>){

    @Entity(tableName = "team")
    data class TeamDBO(
        @PrimaryKey
        val name :String,
        val listId : String
    )
}
