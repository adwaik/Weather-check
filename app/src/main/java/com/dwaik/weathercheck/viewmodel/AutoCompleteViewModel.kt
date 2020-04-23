package com.dwaik.weathercheck.viewmodel

import com.dwaik.weathercheck.model.Repository
import com.dwaik.weathercheck.model.entity.City
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

/**
 * a view model class to fetch and show auto complete suggestions
 * using rx to lessen the requests on network
 */

class AutoCompleteViewModel(private val cityRepo: Repository<City>) : BaseViewModel() {

    private val autoCompleteRelay by lazy { PublishRelay.create<String>() }

    fun onCityQueryChanged(query: String) {
        autoCompleteRelay.accept(query.trim())
    }

    fun autoCompleteData(): Flowable<List<City>> =
        autoCompleteRelay
            .debounce(AUTO_COMPLETE_DEBOUNCE, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { cityRepo.find(it, PAGE_SIZE) }
            .toFlowable(BackpressureStrategy.LATEST)

    companion object {
        private const val AUTO_COMPLETE_DEBOUNCE = 700L
        private const val PAGE_SIZE = 10
    }
}