package com.dwaik.weathercheck

import android.app.Application
import android.content.Context
import com.dwaik.weathercheck.di.managersModule
import com.dwaik.weathercheck.di.repoModule
import com.dwaik.weathercheck.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class WeatherCheck : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            androidContext(this@WeatherCheck)
            modules(repoModule, managersModule, viewModelModule)
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        lateinit var instance: WeatherCheck
        fun appContext(): Context = instance.applicationContext
    }
}