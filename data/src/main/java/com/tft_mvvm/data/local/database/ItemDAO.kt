package com.tft_mvvm.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tft_mvvm.data.local.model.ItemListDBO

@Dao
interface ItemDAO {
    @Query("SELECT * FROM item")
    suspend fun getAllItem(): List<ItemListDBO.ItemDBO>

    @Insert
    suspend fun insertItems(listItems: List<ItemListDBO.ItemDBO>)

    @Query("SELECT * FROM item WHERE itemId LIKE :id")
    suspend fun getItemById(id: String): ItemListDBO.ItemDBO

    @Query("DELETE FROM item")
    suspend fun deleteAllItemTable()

    suspend fun getItemByListId(list: List<String>): List<ItemListDBO.ItemDBO> {
        val array = ArrayList<ItemListDBO.ItemDBO>()
        for (i in list) {
            val item = getItemById(i)
            array.add(item)
        }
        return array
    }
}