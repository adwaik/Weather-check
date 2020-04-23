package com.dwaik.weathercheck.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dwaik.weathercheck.model.room.dao.CityDao
import com.dwaik.weathercheck.model.entity.*

@Database(
    entities = [City::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getCityDao(): CityDao

    companion object {

        private const val DB_NAME = "weather.dbDs"

        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context.applicationContext).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, WeatherDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}