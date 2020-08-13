package com.tft_mvvm.data.local.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.tft_mvvm.data.features.champs.model.Champ
import com.tft_mvvm.data.local.model.ChampListDBO
import java.lang.ref.SoftReference

@Dao
interface ChampDAO {
    @Query("SELECT * FROM champ")
    suspend fun getAllChamp():List<ChampListDBO.ChampDBO>

    @Query("SELECT * FROM champ WHERE origin LIKE:origin")
    suspend fun getDataByOrigin(origin:String):List<ChampListDBO.ChampDBO>

    @Query("SELECT * FROM champ WHERE classs LIKE:classs")
    suspend fun getDataByClass(classs:String):List<ChampListDBO.ChampDBO>

    @Insert(onConflict = REPLACE)
    suspend fun insertChamps(userDBOS: List<ChampListDBO.ChampDBO>)

    @Query("SELECT * FROM champ WHERE id IN (:teamIds)")
    suspend fun getListChampByTeam(teamIds:List<String>):List<ChampListDBO.ChampDBO>

    @Query("DELETE FROM champ")
    suspend fun deleteAllChampTable()

    @Update(onConflict = REPLACE)
    suspend fun updateChamp(champ: ChampListDBO.ChampDBO)

    @Query("SELECT * FROM champ WHERE id LIKE :id")
    suspend fun getChampById(id:String):ChampListDBO.ChampDBO
}