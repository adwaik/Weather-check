package com.dwaik.weathercheck.di

import com.dwaik.weathercheck.model.RepositoryFactory
import com.dwaik.weathercheck.model.entity.City
import com.dwaik.weathercheck.model.retrofit.interceptor.HttpLoggingInterceptor
import com.dwaik.weathercheck.model.retrofit.interceptor.OpenWeatherApiKeyInterceptor
import com.dwaik.weathercheck.util.*
import com.dwaik.weathercheck.viewmodel.AutoCompleteViewModel
import com.dwaik.weathercheck.viewmodel.SelectedCityViewModel
import okhttp3.Interceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repoModule = module {
    single { RepositoryFactory.of<City>() }
}

val managersModule = module {
    single { SelectedCityPrefManagerImp(get()) as SelectedCityPrefManager }
    single { FetcherManagerImp(get()) as FetcherManager }
}

val viewModelModule = module {
    viewModel { AutoCompleteViewModel(get()) }
    viewModel { SelectedCityViewModel(get(), get()) }
}
