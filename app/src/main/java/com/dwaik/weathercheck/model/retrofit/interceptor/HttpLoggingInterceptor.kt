package com.dwaik.weathercheck.model.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

import java.io.IOException

class HttpLoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Timber.d(TAG, request.url().toString())
        return chain.proceed(request)
    }

    companion object {
        private const val TAG = "weather request"
    }
}