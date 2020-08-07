package com.tft_mvvm.data.local.database

import android.service.autofill.LuhnChecksumValidator
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tft_mvvm.data.local.model.ItemListDBO

@Dao
interface ItemDAO {
    @Query("SELECT * FROM item")
    suspend fun getAllItem():List<ItemListDBO.ItemDBO>

    @Insert
    suspend fun insertItems(listItems:List<ItemListDBO.ItemDBO>)

    @Query("SELECT * FROM item WHERE itemId IN (:listId)")
    suspend fun getListItemByListId(listId:List<String>):List<ItemListDBO.ItemDBO>

    @Query("DELETE FROM item")
    suspend fun deleteAllItemTable()
}