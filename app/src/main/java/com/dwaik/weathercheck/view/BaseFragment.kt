package com.dwaik.weathercheck.view

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {

    protected val disposable = CompositeDisposable()

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }
}