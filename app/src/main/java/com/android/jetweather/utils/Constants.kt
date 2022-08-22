package com.android.jetweather.utils

object Constants {
    const val BASE_URL = "https://api.openweathermap.org/"
    const val API_KEY = "ed60fcfbd110ee65c7150605ea8aceea"

    fun <T> getTAG(obj: T): String {
        return obj!!::class.java.canonicalName as String
    }
}