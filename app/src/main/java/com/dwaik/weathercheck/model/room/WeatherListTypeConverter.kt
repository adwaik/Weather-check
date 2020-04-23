package com.dwaik.weathercheck.model.room

import androidx.room.TypeConverter
import com.dwaik.weathercheck.model.entity.Weather
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson

class WeatherListTypeConverter {

    @TypeConverter
    fun fromWeatherList(weatherList: List<Weather>?): String? {
        if (weatherList == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Weather>>() {

        }.type
        return gson.toJson(weatherList, type)
    }

    @TypeConverter
    fun toWeatherList(weatherListString: String?): List<Weather>? {
        if (weatherListString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Weather>>() {

        }.type
        return gson.fromJson(weatherListString, type)
    }
}