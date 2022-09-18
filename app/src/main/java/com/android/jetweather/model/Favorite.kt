package com.android.jetweather.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "favorite_table")
data class Favorite(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Long = 0,
    @NonNull
    @ColumnInfo(name = "city")
    val city: String,
    @NonNull
    @ColumnInfo(name = "country")
    val country: String
)
