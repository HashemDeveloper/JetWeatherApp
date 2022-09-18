package com.android.jetweather.repository.local

import com.android.jetweather.data.WeatherDao
import com.android.jetweather.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherLocalRepo @Inject constructor(private val weatherDao: WeatherDao) {
    fun getFavorite(): Flow<MutableList<Favorite>> = this.weatherDao.getFavorite()
    suspend fun getFavoriteByCity(city: String): Favorite = this.weatherDao.getFavoriteByCity(city)
    suspend fun insertFavorite(favorite: Favorite) = this.weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = this.weatherDao.updateFavorite(favorite)
    suspend fun deleteFavoriteByCity(favorite: Favorite) = this.weatherDao.deleteFavoriteByCity(favorite)
    suspend fun deleteAllFavorite() = this.weatherDao.deleteAllFavorite()
}