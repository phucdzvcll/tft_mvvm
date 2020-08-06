package com.tft_mvvm.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tft_mvvm.data.local.model.ClassAndOriginListDBO

@Dao
interface ClassAndOriginDAO {
    @Query("SELECT * FROM classAndOrigin")
    suspend fun getAllClassAndOrigin(): List<ClassAndOriginListDBO.ClassAndOrigin>

    @Insert
    suspend fun insertClassAndOrigin(list: List<ClassAndOriginListDBO.ClassAndOrigin>)

    @Query("SELECT * FROM classAndOrigin WHERE classOrOriginName LIKE :classOrOriginName")
    suspend fun getClassOrOriginByName(classOrOriginName: String): ClassAndOriginListDBO.ClassAndOrigin

    @Query("DELETE FROM classAndOrigin")
    suspend fun deleteAllClassAndOrinTable()
}