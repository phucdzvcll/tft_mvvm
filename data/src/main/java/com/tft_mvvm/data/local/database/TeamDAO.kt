package com.tft_mvvm.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tft_mvvm.data.local.model.TeamListDBO

@Dao
interface TeamDAO {
    @Query("SELECT * FROM team")
    suspend fun getAllTeam(): List<TeamListDBO.TeamDBO>

    @Insert
    suspend fun insertTeam(teamDBOs: List<TeamListDBO.TeamDBO>)
}