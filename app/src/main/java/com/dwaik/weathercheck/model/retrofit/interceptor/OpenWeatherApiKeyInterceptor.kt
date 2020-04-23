package com.dwaik.weathercheck.model.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Response

import java.io.IOException

class OpenWeatherApiKeyInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter(
                WEATHER_API_KEY_PARAM,
                WEATHER_API_KEY_VALUE
            ).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    companion object {
        const val WEATHER_API_KEY_PARAM="appid"
        const val WEATHER_API_KEY_VALUE="0c6bdba87df46fae67acf82f03bd7c9b"
    }
}
