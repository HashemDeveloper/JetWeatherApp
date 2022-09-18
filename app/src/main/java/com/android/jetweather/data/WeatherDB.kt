package com.android.jetweather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.jetweather.model.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class WeatherDB: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}