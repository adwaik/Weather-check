package com.dwaik.weathercheck.model.entity

import androidx.room.*
import com.dwaik.weathercheck.model.room.WeatherListTypeConverter

@Entity(tableName = "cities")
@TypeConverters(WeatherListTypeConverter::class)
data class City(
    @PrimaryKey val id: Long,
    val name: String = "",
    val base: String? = null,
    val visibility: Int = 0,
    val dt: Long = 0,
    val timezone: Int = 0,
    val cod: Int = 0,
    val weather: List<Weather>,
    @Embedded(prefix = "coord_") val coord: Coordintes,
    @Embedded(prefix = "wind_") val wind: Wind,
    @Embedded(prefix = "sys_") val sys: Sys,
    @Embedded(prefix = "main_") val main: Main
) {
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is City) other.id == id else false
    }

    override fun toString(): String {
        return "$name, ${sys.country}"
    }
}
