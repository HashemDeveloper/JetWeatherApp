package com.android.jetweather.utils

import com.android.jetweather.model.WeatherInfo
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(timeStamp: Int): String {
    val sdf = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
    val date = Date(timeStamp.toLong() * 1000)
    return sdf.format(date)
}
fun formatDateTime(timeStamp: Int): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date = Date(timeStamp.toLong() * 1000)
    return sdf.format(date)
}
fun formatDecimals(item: Double): String {
    return " %.0f".format(item)
}
fun formatDayOfTheWeek(timeStamp: Int): String {
    val sdf = SimpleDateFormat("EEE", Locale.getDefault())
    val date = Date(timeStamp.toLong() * 1000)
    return sdf.format(date)
}
fun getSevenDays(): MutableList<String> {
    val sdf = SimpleDateFormat("EEE", Locale.getDefault())
    val dayList: MutableList<String> = mutableListOf()
    for (i in 0 until 7) {
        val calendar = GregorianCalendar()
        calendar.add(Calendar.DATE,i)
        val day: String = sdf.format(calendar.time)
        dayList.add(day)
    }
    return dayList
}
fun iconUrl(icon: String): String {
    return "https://openweathermap.org/img/wn/${icon}.png"
}
