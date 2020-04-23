package com.dwaik.weathercheck.util


interface SelectedCityPrefManager {
    fun setSelectedCity(city: String)
    fun getSelectedCity(): String?
}