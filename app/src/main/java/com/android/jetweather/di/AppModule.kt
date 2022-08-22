package com.android.jetweather.di

import com.android.jetweather.network.WeatherAPI
import com.android.jetweather.repository.WeatherRepository
import com.android.jetweather.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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