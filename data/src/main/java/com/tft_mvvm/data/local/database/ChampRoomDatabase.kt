package com.tft_mvvm.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tft_mvvm.data.local.model.ChampListDBO

@Database(entities = [ChampListDBO.ChampDBO::class], version = 1)

abstract class ChampRoomDatabase : RoomDatabase() {

    abstract fun champDAO(): ChampDAO

    companion object {

        private var INSTANCE: ChampRoomDatabase? = null

        fun getInstance(context: Context): ChampRoomDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    ChampRoomDatabase::class.java,
                    "Champ Manager"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE!!
        }
    }
}