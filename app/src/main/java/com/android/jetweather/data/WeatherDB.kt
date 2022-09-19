package com.android.jetweather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.jetweather.model.Favorite
import com.android.jetweather.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDB: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun settingUnitDao(): SettingUnitDao
}