package com.dwaik.weathercheck.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.dwaik.weathercheck.R
import com.dwaik.weathercheck.util.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val fetcherManager: FetcherManager by inject()
    private val prefManager: SelectedCityPrefManager by inject()
    private val navigationManager: NavigationManager by lazy {
        NavigationManagerImp(nav_host_fragment as NavHostFragment, prefManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationManager.setNavigationHome()
        fetcherManager.schedule()
    }

}
