package com.eduardo.chucknorris.data.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

open class NetworkUtils(private val ctx: Context) {

    @SuppressLint("MissingPermission")
    open fun isNetworkAvailable(): Boolean{
        val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnected
    }

}