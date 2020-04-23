package com.dwaik.weathercheck.model.room

import com.dwaik.weathercheck.model.room.dao.CityDao
import com.dwaik.weathercheck.model.entity.City
import com.dwaik.weathercheck.model.DataSource
import io.reactivex.Completable
import io.reactivex.Observable

class DbDataSource(val dao: CityDao) : DataSource<City> {

    override fun find(query: String, cnt: Int): Observable<List<City>> {
        return dao.find("$query%").toObservable()
    }

    override fun get(query: String): Observable<City> {
        return dao.get(query).toObservable()
    }

    override fun insert(item: City): Observable<City> {
        return Completable.fromCallable { dao.insert(item) }
            .andThen(Observable.just(item))
    }

    override fun delete(item: City): Completable {
        return Completable.fromCallable { dao.delete(item) }
    }

    override fun deleteAll(): Completable {
        return Completable.fromCallable { dao.deleteAll() }
    }
}