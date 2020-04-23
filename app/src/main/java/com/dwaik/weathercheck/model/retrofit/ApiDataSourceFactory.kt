package com.dwaik.weathercheck.model.retrofit

import com.dwaik.weathercheck.model.DataSource
import com.dwaik.weathercheck.model.entity.City
import kotlin.reflect.KClass

object ApiDataSourceFactory {

    @Suppress("Unchecked_cast")
    fun <Entity : Any> of(clazz: KClass<*>): DataSource<Entity> {
        return when (clazz) {
            City::class -> ApiDataSource()
            else -> throw IllegalArgumentException("Unsupported data type")
        } as DataSource<Entity>
    }
}