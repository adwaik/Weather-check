package com.dwaik.weathercheck.util

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.dwaik.weathercheck.service.WeatherFetcher
import java.util.concurrent.TimeUnit

class FetcherManagerImp(private val context: Context) : FetcherManager {
    override fun schedule() {
        with(
            PeriodicWorkRequest
                .Builder(WeatherFetcher::class.java, DAY, TimeUnit.HOURS)
                .build()
        ) {
            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    FETCHER_TAG,
                    ExistingPeriodicWorkPolicy.KEEP,
                    this
                )
        }
    }

    companion object {
        private const val FETCHER_TAG = "weather_fetcher"
        private const val DAY = 24L
    }
}