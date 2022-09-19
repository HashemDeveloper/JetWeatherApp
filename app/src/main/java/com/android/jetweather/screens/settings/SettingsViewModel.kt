package com.android.jetweather.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jetweather.model.Unit
import com.android.jetweather.repository.local.SettingsLocalRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsLocalRepo: SettingsLocalRepo): ViewModel() {
    private val _units: MutableStateFlow<MutableList<Unit>> = MutableStateFlow(mutableListOf())
    val units: StateFlow<MutableList<Unit>> = _units.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            this@SettingsViewModel.settingsLocalRepo.getUnits().distinctUntilChanged()
                .collect {
                    if (it.isNotEmpty()) {
                        _units.value = it
                    }
                }
        }
    }

    fun insertUnit(unit: Unit) = this.viewModelScope.launch(Dispatchers.IO) {
        this@SettingsViewModel.settingsLocalRepo.insertUnit(unit)
    }
    fun updateUnit(unit: Unit) = this.viewModelScope.launch(Dispatchers.IO) {
        this@SettingsViewModel.settingsLocalRepo.updateUnit(unit)
    }
    fun deleteUnit(unit: Unit) = this.viewModelScope.launch(Dispatchers.IO) {
        this@SettingsViewModel.settingsLocalRepo.deleteUnit(unit)
    }

    fun deleteAllUnit() = this.viewModelScope.launch(Dispatchers.IO) {
        this@SettingsViewModel.settingsLocalRepo.deleteAllUnits()
    }
}