package com.dwaik.weathercheck.adapter

import android.widget.TextView
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.NonNull
import com.dwaik.weathercheck.R
import com.dwaik.weathercheck.model.entity.City
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable


class SuggestionAdapter(
    context: Context,
    private val resourceId: Int,
    var items: List<City>
) : ArrayAdapter<City>(context, resourceId, items) {

    val selectedCityRelay = PublishRelay.create<City>()

    @NonNull
    override fun getView(position: Int, convertView: View?, @NonNull parent: ViewGroup): View? {
        return convertView ?: LayoutInflater.from(context)
            .inflate(resourceId, parent, false).apply {
                val city = getItem(position)
                val name = findViewById<TextView>(R.id.tv_suggest)
                name.setText(city.toString())
                RxView.clicks(this).subscribe {
                    selectedCityRelay.accept(city)
                }
            }

    }

    override fun getItem(position: Int): City? {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun selectedSuggestion(): Flowable<City> = selectedCityRelay.toFlowable(BackpressureStrategy.LATEST)

}