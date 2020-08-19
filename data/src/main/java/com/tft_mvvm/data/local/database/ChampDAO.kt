package com.tft_mvvm.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.tft_mvvm.data.local.model.ChampListDBO

@Dao
interface ChampDAO {
    @Query("SELECT * FROM champ")
    suspend fun getAllChamp(): List<ChampListDBO.ChampDBO>

    @Query("SELECT * FROM champ WHERE originAndClassName LIKE '%' || :originAndClassName || '%' ")
    suspend fun getListChampByClassOrOriginName(originAndClassName: String): List<ChampListDBO.ChampDBO>

    @Insert(onConflict = REPLACE)
    suspend fun insertChamps(userDBOS: List<ChampListDBO.ChampDBO>)

    @Query("SELECT * FROM champ WHERE id IN (:teamIds)")
    suspend fun getListChampByTeam(teamIds: List<String>): List<ChampListDBO.ChampDBO>

    @Query("DELETE FROM champ")
    suspend fun deleteAllChampTable()

    @Query("SELECT * FROM champ WHERE id LIKE :id")
    suspend fun getChampById(id: String): ChampListDBO.ChampDBO
}