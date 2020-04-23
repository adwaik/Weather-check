package com.dwaik.weathercheck.model.retrofit

import com.dwaik.weathercheck.model.DataSource
import com.dwaik.weathercheck.model.entity.ApiWrapper
import com.dwaik.weathercheck.model.entity.City
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.Completable
import io.reactivex.Observable

class ApiDataSource() : DataSource<City> {

    val apiInterface = ApiClientFactory.apiClient.create(WeatherApiInterface::class.java)

    override fun find(query: String, cnt: Int): Observable<List<City>> {
        return apiInterface.find(query, PAGE_SIZE)
            .onErrorReturn {
                (it as HttpException).run {
                    ApiWrapper(code().toString(), message())
                }
            }
            .filter { HTTP_SUCCESS == it.cod }
            .map { it.list }
    }

    override fun get(query: String): Observable<City> {
        return apiInterface.getCity(query)
    }

    override fun insert(item: City): Observable<City> {
        //no insertion will happen on server
        return Observable.just(item)
    }

    override fun delete(item: City): Completable {
        //no deletion will happen on server
        return Completable.complete()
    }

    override fun deleteAll(): Completable {
        //no deletion will happen on server
        return Completable.complete()
    }

    companion object {
        const val PAGE_SIZE = 10
        private const val HTTP_SUCCESS = "200"
    }
}