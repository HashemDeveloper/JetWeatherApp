package com.android.jetweather.repository.local

import com.android.jetweather.data.SettingUnitDao
import com.android.jetweather.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsLocalRepo @Inject constructor(private val settingUnitDao: SettingUnitDao) {
    fun getUnits(): Flow<MutableList<Unit>> = this.settingUnitDao.getUnits()
    suspend fun insertUnit(unit: Unit) = this.settingUnitDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = this.settingUnitDao.updateUnit(unit)
    suspend fun deleteUnit(unit: Unit) = this.settingUnitDao.deleteUnit(unit)
    suspend fun deleteAllUnits() = this.settingUnitDao.deleteAllUnit()
}