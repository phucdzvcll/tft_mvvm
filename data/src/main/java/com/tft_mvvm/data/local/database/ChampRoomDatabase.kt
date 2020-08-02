package com.tft_mvvm.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tft_mvvm.data.local.model.ChampListDBO
import com.tft_mvvm.data.local.model.TeamListDBO

@Database(entities = [ChampListDBO.ChampDBO::class, TeamListDBO.TeamDBO::class], version = 1, exportSchema = false)

abstract class ChampRoomDatabase : RoomDatabase() {

    abstract fun champDAO(): ChampDAO

    abstract fun teamDAO():TeamDAO

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