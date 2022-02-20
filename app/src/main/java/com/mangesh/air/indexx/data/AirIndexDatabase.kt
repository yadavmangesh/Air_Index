package com.mangesh.air.indexx.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mangesh.air.indexx.data.dao.AirIndexDao
import com.mangesh.air.indexx.data.enitity.AirIndex

@Database(entities = [AirIndex::class],version = 1,exportSchema = false)
abstract class AirIndexDatabase:RoomDatabase() {

    abstract fun getAirIndex():AirIndexDao

    companion object{
        @Volatile
        private var INSTANCE: AirIndexDatabase? = null

        fun getDataBase(context: Context): AirIndexDatabase {
            if (INSTANCE == null) {
                synchronized(AirIndexDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AirIndexDatabase::class.java,
                        "air_index"
                    )
                        .build()
                    return INSTANCE as AirIndexDatabase

                }
            } else return INSTANCE as AirIndexDatabase
        }
    }
}