package com.dwaik.weathercheck.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather(
    @PrimaryKey val id: Int,
    val main: String,
    val description: String,
    val icon: String)



