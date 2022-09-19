package com.android.jetweather.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jetweather.model.Favorite
import com.android.jetweather.repository.local.WeatherLocalRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val weatherDbRepo: WeatherLocalRepo) :
    ViewModel() {
    private val _favList: MutableStateFlow<MutableList<Favorite>> =
        MutableStateFlow(mutableListOf())
    val favList: StateFlow<MutableList<Favorite>> = _favList.asStateFlow()

    init {
        this.viewModelScope.launch(Dispatchers.IO) {
            weatherDbRepo.getFavorite()
                .distinctUntilChanged()
                .collect {
                    if (it.isNotEmpty()) {
                        _favList.value = it
                    }
                }
        }
    }
    fun insertFavorite(favorite: Favorite) = this.viewModelScope.launch {
        this@FavoriteViewModel.weatherDbRepo.insertFavorite(favorite)
    }
    fun updateFavorite(favorite: Favorite) = this.viewModelScope.launch {
        this@FavoriteViewModel.weatherDbRepo.updateFavorite(favorite)
    }
    fun deleteFavorite(favorite: Favorite) = this.viewModelScope.launch {
        this@FavoriteViewModel.weatherDbRepo.deleteFavoriteByCity(favorite)
    }
    fun deleteAll(favorite: Favorite) = this.viewModelScope.launch {
        this@FavoriteViewModel.weatherDbRepo.deleteAllFavorite()
    }
    fun getFavoriteByCity(city: String) = this.viewModelScope.launch {
        this@FavoriteViewModel.weatherDbRepo.getFavoriteByCity(city)
    }
}