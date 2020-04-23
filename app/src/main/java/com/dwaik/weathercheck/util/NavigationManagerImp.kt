package com.dwaik.weathercheck.util

import androidx.navigation.fragment.NavHostFragment
import com.dwaik.weathercheck.R

class NavigationManagerImp(
    private val navHost: NavHostFragment,
    private val selectedCityPrefManager: SelectedCityPrefManager
) : NavigationManager {

    override fun setNavigationHome() {
        if (selectedCityPrefManager.getSelectedCity() == null) {

            val graphInflater = navHost.navController.navInflater
            val navGraph = graphInflater.inflate(R.navigation.nav_graph)
            val navController = navHost.navController
            navGraph.startDestination = R.id.selectionFragment
            navController.graph = navGraph
        }
    }
}