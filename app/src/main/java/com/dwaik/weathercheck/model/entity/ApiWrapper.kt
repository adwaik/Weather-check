package com.dwaik.weathercheck.model.entity

data class ApiWrapper(
    val cod: String,
    val message: String,
    val count: Int?=0,
    val list: List<City>?=null
)
