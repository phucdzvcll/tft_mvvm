package com.tft_mvvm.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tft_mvvm.data.features.champs.model.Champ
import com.tft_mvvm.data.local.model.ChampListDBO
import java.lang.ref.SoftReference

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

    @Query("SELECT * FROM champ WHERE id IN (:teamIds)")
    suspend fun getListChampByTeam(teamIds:List<String>):List<ChampListDBO.ChampDBO>

}