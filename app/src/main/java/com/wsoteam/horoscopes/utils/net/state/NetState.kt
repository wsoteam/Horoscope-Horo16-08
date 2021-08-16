package com.wsoteam.horoscopes.utils.net.state

import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.widget.Toast
import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.R

object NetState {

    fun isConnected() : Boolean{
        val cm =
            App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var wifiInfo =
            cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.activeNetworkInfo
        return wifiInfo != null && wifiInfo.isConnected
    }

    fun showNetLost(context: Context){
        Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_SHORT).show()
    }
}