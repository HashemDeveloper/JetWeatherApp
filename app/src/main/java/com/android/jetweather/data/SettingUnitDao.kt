package com.android.jetweather.data

import androidx.room.*
import com.android.jetweather.model.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingUnitDao {

    @Query("select * from settings_table")
    fun getUnits(): Flow<MutableList<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Delete
    suspend fun deleteUnit(unit: Unit)

    @Query("delete from settings_table")
    suspend fun deleteAllUnit()
}