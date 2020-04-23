package com.dwaik.weathercheck.model.room

import com.dwaik.weathercheck.WeatherCheck
import com.dwaik.weathercheck.model.DataSource
import com.dwaik.weathercheck.model.entity.City
import kotlin.reflect.KClass

object DbDataSourceFactory {

    val db: WeatherDatabase by lazy { WeatherDatabase.getInstance(WeatherCheck.appContext()) }

    @Suppress("Unchecked_cast")
    fun <Entity : Any> of(clazz: KClass<*>): DataSource<Entity> {
        return when (clazz) {
            City::class -> DbDataSource(db.getCityDao())
            else -> throw IllegalArgumentException("Unsupported data type")
        } as DataSource<Entity>
    }

    fun clearDb() {
        db.clearAllTables()
    }
}