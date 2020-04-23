package com.dwaik.weathercheck.model.retrofit

import com.dwaik.weathercheck.model.retrofit.interceptor.OpenWeatherApiKeyInterceptor
import com.dwaik.weathercheck.model.retrofit.interceptor.HttpLoggingInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

object ApiClientFactory {
    private val BASE_URL = "http://api.openweathermap.org/"
    private val REQUEST_TIMEOUT = 60

    val apiClient: Retrofit by lazy {
        val client = OkHttpClient.Builder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(OpenWeatherApiKeyInterceptor())
            .addInterceptor(HttpLoggingInterceptor())
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
