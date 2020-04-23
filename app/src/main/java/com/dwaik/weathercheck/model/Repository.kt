package com.dwaik.weathercheck.model

import com.dwaik.weathercheck.util.isNetworkAvailable
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class Repository<T>(
    private val apiDs: DataSource<T>,
    private val dbDs: DataSource<T>
) : DataSource<T> {

    override fun find(query: String, cnt: Int): Observable<List<T>> {
        return Observable.defer {
            if (isNetworkAvailable())
                apiDs.find(query, cnt)
                    .doOnNext { Timber.d("getting ${it.size} records from api") }
                    .subscribeOn(Schedulers.io())
            else
                Observable.empty()
        }.subscribeOn(Schedulers.io())
    }

    override fun get(cityName: String): Observable<T> {
        return Observable.concatArrayEager(
            dbDs.get(cityName)
                .doOnNext { Timber.d("found record from db") }
                .subscribeOn(Schedulers.io()),
            Observable.defer {
                if (isNetworkAvailable())
                    apiDs.get(cityName)
                        .doOnNext { Timber.d("getting record from api") }
                        .subscribeOn(Schedulers.io())
                        .flatMap { l ->
                            dbDs.get(cityName)
                                .flatMapCompletable { old ->
                                    dbDs.delete(old)
                                        .doOnComplete {
                                            Timber.d("deleted old cache data")
                                        }
                                }
                                .andThen(
                                    dbDs.insert(l)
                                        .doOnComplete {
                                            Timber.d("inserted fresh records into cache")
                                        }
                                )
                        }
                else
                    Observable.empty()
            }.subscribeOn(Schedulers.io())
        )
    }

    override fun insert(item: T): Observable<T> {
        return Observable.defer {
            dbDs.insert(item).subscribeOn(Schedulers.io())
        }
    }

    override fun deleteAll(): Completable {
        return Completable.defer {
            dbDs.deleteAll().subscribeOn(Schedulers.io())
        }
    }

    override fun delete(item: T): Completable {
        return Completable.defer {
            dbDs.delete(item).subscribeOn(Schedulers.io())
        }
    }
}