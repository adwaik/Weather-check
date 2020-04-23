package com.dwaik.weathercheck.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Main(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int,
    val grnd_level: Int,
    val icon: Int
)
