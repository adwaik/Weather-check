package com.dwaik.weathercheck.service

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dwaik.weathercheck.model.Repository
import com.dwaik.weathercheck.model.entity.City
import com.dwaik.weathercheck.util.SelectedCityPrefManager
import org.koin.core.KoinComponent
import org.koin.core.inject


class WeatherFetcher(
    context: Context,
    workerParams: WorkerParameters
) :
    Worker(context, workerParams), KoinComponent {

    private val cityRepo: Repository<City> by inject()
    private val selectedCityPrefManager: SelectedCityPrefManager by inject()

    override fun doWork(): Result {


        val prefCity = selectedCityPrefManager.getSelectedCity()
        if (prefCity != null) {
            cityRepo.get(prefCity).subscribe()
        }
        val outputData = Data.Builder().putString(WORK_RESULT, "weather updated").build()
        return Result.success(outputData)
    }

    companion object {
        private val WORK_RESULT = "work_result"
    }
}
