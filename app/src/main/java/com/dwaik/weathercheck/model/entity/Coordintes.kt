package com.dwaik.weathercheck.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coordintes(
    val lat: Double,
    val lon: Double,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
    )