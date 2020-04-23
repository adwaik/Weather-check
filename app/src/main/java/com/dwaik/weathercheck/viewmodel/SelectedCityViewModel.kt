package com.dwaik.weathercheck.viewmodel

import androidx.lifecycle.MutableLiveData
import com.dwaik.weathercheck.WeatherCheck
import com.dwaik.weathercheck.model.Repository
import com.dwaik.weathercheck.model.RepositoryFactory
import com.dwaik.weathercheck.model.entity.City
import com.dwaik.weathercheck.util.SelectedCityPrefManager
import com.dwaik.weathercheck.util.SelectedCityPrefManagerImp
import io.reactivex.rxkotlin.addTo

/**
 * a view model class to fetch and show selected city
 * using live data for binding ui
 */
class SelectedCityViewModel(
    private val cityRepo: Repository<City>,
    private val selectedCityPrefManager: SelectedCityPrefManager
) : BaseViewModel() {

    val selectedCity by lazy { MutableLiveData<City>() }

    init {
        selectedCityPrefManager.getSelectedCity()?.let {
            cityRepo.get(it).subscribe { city ->
                selectedCity.postValue(city)
            }.addTo(disposable)
        }
    }

    fun commitSelectedCity(city: City) {
        cityRepo.deleteAll()
            .andThen(cityRepo.insert(city))
            .subscribe {
                selectedCityPrefManager.setSelectedCity(it.name)
            }.addTo(disposable)
    }

    fun selectCity(city: City) {
        selectedCity.postValue(city)
    }

}