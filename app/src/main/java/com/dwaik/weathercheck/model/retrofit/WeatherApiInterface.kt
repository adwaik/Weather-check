package com.dwaik.weathercheck.model.retrofit

import com.dwaik.weathercheck.model.entity.ApiWrapper
import com.dwaik.weathercheck.model.entity.City
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {

    @GET("data/2.5/find")
    fun find(
        @Query("q") query: String,
        @Query("amp;cnt") cnt: Int
    ): Observable<ApiWrapper>

    @GET("data/2.5/weather")
    fun getCity(
        @Query("q") query: String
    ): Observable<City>

}
