package com.android.jetweather.data

import androidx.room.*
import com.android.jetweather.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("select * from favorite_table")
    fun getFavorite(): Flow<MutableList<Favorite>>

    @Query("select * from favorite_table where id =:city")
    suspend fun getFavoriteByCity(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavoriteByCity(city: Favorite)

    @Query("delete from favorite_table")
    suspend fun deleteAllFavorite(): Int
}