package com.dwaik.weathercheck.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dwaik.weathercheck.R
import com.dwaik.weathercheck.WeatherCheck


fun isNetworkAvailable(): Boolean {
    val cm = WeatherCheck.instance.applicationContext
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo?.isConnected ?: false
}

fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

@BindingAdapter("android:text")
fun setFloat(view: TextView, value: Float) {
    if (value == Float.NaN) view.text = ""
    else view.text = value.toString()
}

@InverseBindingAdapter(attribute = "android:text")
fun getFloat(view: TextView): Float {
    val num = view.text.toString();
    if (num.isEmpty()) return 0.0F;
    try {
        return num.toFloat()
    } catch (e: NumberFormatException) {
        return 0.0F;
    }
}

@BindingAdapter("android:text")
fun setInt(view: TextView, value: Int) {
    view.text = value.toString()
}

@InverseBindingAdapter(attribute = "android:text")
fun getInt(view: TextView): Int {
    val num = view.text.toString();
    if (num.isEmpty()) return 0;
    try {
        return num.toInt()
    } catch (e: NumberFormatException) {
        return 0;
    }
}

@BindingAdapter("weatherImage")
fun loadWeatherImage(view: ImageView, imageUrl: String) {
    Glide.with(view.getContext())
        .load("http://api.openweathermap.org/img/w/$imageUrl.png")
        .placeholder(R.mipmap.ic_launcher_round)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view)
}