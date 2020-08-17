package com.tft_mvvm.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

class TeamListDBO(val teamDBOs: List<TeamDBO>) {

    @Entity(tableName = "team")
    data class TeamDBO(
        @PrimaryKey
        val idTeam: String,
        val nameTeam: String,
        val idChampMain: String,
        val idItemSuitable: String,
        val listIdThreeStar : String,
        val listIdChamp: String
    )
}
