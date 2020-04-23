package com.dwaik.weathercheck.model

import io.reactivex.Completable
import io.reactivex.Observable

interface DataSource<T> {
    fun find(query: String, cnt: Int): Observable<List<T>>

    fun get(query: String): Observable<T>

    fun insert(item: T): Observable<T>

    fun delete(item: T): Completable

    fun deleteAll(): Completable

}