package com.dwaik.weathercheck.model

import com.dwaik.weathercheck.model.retrofit.ApiDataSourceFactory
import com.dwaik.weathercheck.model.room.DbDataSourceFactory
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

object RepositoryFactory{
    inline fun <reified Entity : Any> of(): Repository<Entity> {
        return Repository(ApiDataSourceFactory.of(Entity::class),
            DbDataSourceFactory.of(Entity::class)
        )
    }
}