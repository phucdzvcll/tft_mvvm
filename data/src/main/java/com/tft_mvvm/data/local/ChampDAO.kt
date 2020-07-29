package com.tft_mvvm.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tft_mvvm.data.local.model.ChampListDBO

@Dao
interface ChampDAO {
    @Query("SELECT * FROM champ")
    suspend fun getData():List<ChampListDBO.ChampDBO>

    @Insert
    suspend fun insertChamps(userDBOS: List<ChampListDBO.ChampDBO>)
}