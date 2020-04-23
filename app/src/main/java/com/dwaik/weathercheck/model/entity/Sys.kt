package com.dwaik.weathercheck.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sys(
    @PrimaryKey val id: Int,
    val type: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
