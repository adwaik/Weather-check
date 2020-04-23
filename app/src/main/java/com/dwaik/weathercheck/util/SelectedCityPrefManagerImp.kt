package com.dwaik.weathercheck.util

import android.content.Context

class SelectedCityPrefManagerImp(private val context: Context) : SelectedCityPrefManager {

    override fun setSelectedCity(city: String) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(pref.edit()) {
            putString(PREF_KEY_CITY, city)
            apply()
        }
    }

    override fun getSelectedCity(): String? {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getString(PREF_KEY_CITY, null)
    }

    companion object {
        const val PREF_NAME = "weather_check"
        const val PREF_KEY_CITY = "selected_city"
    }
}