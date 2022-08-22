package com.android.jetweather.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jetweather.data.DataOrException
import com.android.jetweather.model.WeatherInfo
import com.android.jetweather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repo: WeatherRepository): ViewModel() {
    val data: MutableState<DataOrException<WeatherInfo,Boolean,Exception>> = mutableStateOf(
        DataOrException(null,true,Exception("")))
    init {
        loadInitialData()
    }
    private fun loadInitialData() {
        viewModelScope.launch(Dispatchers.IO) {
            getWeather("Brooklyn")
        }
    }
    fun getWeather(city: String) {
        if (city.isEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            data.value = repo.getWeather(city)
        }
    }
}