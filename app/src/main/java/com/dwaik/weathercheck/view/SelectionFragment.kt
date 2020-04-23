package com.dwaik.weathercheck.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.dwaik.weathercheck.R
import com.dwaik.weathercheck.adapter.SuggestionAdapter
import com.dwaik.weathercheck.model.entity.City
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.frag_selection.*
import com.jakewharton.rxbinding.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.dwaik.weathercheck.util.hideKeyboard
import com.dwaik.weathercheck.viewmodel.AutoCompleteViewModel
import com.dwaik.weathercheck.viewmodel.SelectedCityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

open class SelectionFragment : BaseFragment(), OnMapReadyCallback {

    private val atViewModel: AutoCompleteViewModel by viewModel()
    private val scViewModel: SelectedCityViewModel by sharedViewModel()
    private var city: City? = null

    private val adapter by lazy {
        SuggestionAdapter(
            context!!,
            R.layout.item_suggesion,
            listOf()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupSubscribers()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        subscribeCitySelection(googleMap)
    }

    private fun setupUI() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        et_city.setAdapter(adapter)
    }

    private fun setupSubscribers() {
        subscribeClicks()
        subscribeTextChange()
        subscribeAutoComplete()
        subscribeSuggestionSelection()
    }

    private fun subscribeClicks() {
        RxView.clicks(btn_start)
            .subscribe {
                scViewModel.commitSelectedCity(city!!)
                Navigation.findNavController(view!!).navigate(R.id.action_details)
            }
    }

    private fun subscribeTextChange() {
        RxTextView.textChangeEvents(et_city)
            .filter { it.text().toString().length > 2 && !it.text().contains(",") }
            .subscribe {
                atViewModel.onCityQueryChanged(it.text().toString())
            }
    }

    private fun subscribeAutoComplete() {
        atViewModel.autoCompleteData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                adapter.apply {
                    items = list
                    notifyDataSetChanged()
                }

                if (list.isNotEmpty()) {
                    et_city.showDropDown()
                } else {
                    et_city.dismissDropDown()
                }
            }.addTo(disposable)
    }

    private fun subscribeSuggestionSelection() {
        adapter.selectedSuggestion()
            .doOnNext {
                et_city.dismissDropDown()
                hideKeyboard(activity!!)
            }
            .subscribe {
                scViewModel.selectCity(it)
            }
            .addTo(disposable)
    }

    private fun subscribeCitySelection(map: GoogleMap) {
        val nameObserver = Observer<City> {
            btn_start.setEnabled(true)
            city = it.also {
                et_city.setText(it.toString())
                placeMarker(map, it)
            }
        }
        scViewModel.selectedCity.observe(this, nameObserver)
    }

    private fun placeMarker(map: GoogleMap, city: City) {
        val cityLoc = LatLng(city.coord.lat, city.coord.lon)
        map.clear()
        map.addMarker(MarkerOptions().position(cityLoc))
        map.moveCamera(CameraUpdateFactory.newLatLng(cityLoc))
    }
}
