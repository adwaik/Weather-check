package com.dwaik.weathercheck.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.dwaik.weathercheck.R
import com.dwaik.weathercheck.databinding.FragDetailsBinding
import com.dwaik.weathercheck.viewmodel.SelectedCityViewModel
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.frag_details.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

open class DetailsFragment : BaseFragment() {

    private val viewModel: SelectedCityViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragDetailsBinding = DataBindingUtil.inflate(
            inflater, R.layout.frag_details, container, false
        )

        with(binding){
            viewmodel = viewModel
            lifecycleOwner = this@DetailsFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeClicks()
    }

    private fun subscribeClicks() {
        RxView.clicks(btn_change_city)
            .subscribe {
                Navigation.findNavController(view!!).navigate(R.id.action_selection)
            }
    }
}
