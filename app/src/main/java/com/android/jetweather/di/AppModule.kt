package com.android.jetweather.di

import android.content.Context
import androidx.room.Room
import com.android.jetweather.data.SettingUnitDao
import com.android.jetweather.data.WeatherDB
import com.android.jetweather.data.WeatherDao
import com.android.jetweather.network.WeatherAPI
import com.android.jetweather.repository.remote.WeatherRepository
import com.android.jetweather.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDb: WeatherDB): WeatherDao = weatherDb.weatherDao()
    @Singleton
    @Provides
    fun provideSettingUnitDao(weatherDb: WeatherDB): SettingUnitDao = weatherDb.settingUnitDao()
    @Singleton
    @Provides
    fun provideWeatherDB(@ApplicationContext context: Context): WeatherDB =
        Room.databaseBuilder(context, WeatherDB::class.java, "Weather_DB")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideWeatherRepo(api: WeatherAPI) = WeatherRepository(api)

    @Singleton
    @Provides
    fun provideWeatherAPI(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }
}