package com.dwaik.weathercheck.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wind(
    val speed: Float,
    val deg: Int,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
