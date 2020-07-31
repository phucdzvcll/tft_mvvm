package com.tft_mvvm.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tft_mvvm.data.local.model.ChampListDBO

@Dao
interface ChampDAO {
    @Query("SELECT * FROM champ")
    suspend fun getData():List<ChampListDBO.ChampDBO>

    @Query("SELECT * FROM champ WHERE origin LIKE:origin")
    suspend fun getDataByOrigin(origin:String):List<ChampListDBO.ChampDBO>

    @Query("SELECT * FROM champ WHERE classs LIKE:classs")
    suspend fun getDataByClass(classs:String):List<ChampListDBO.ChampDBO>

    @Insert
    suspend fun insertChamps(userDBOS: List<ChampListDBO.ChampDBO>)
}