package com.wsoteam.horoscopes.utils

import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider

object PriceManager {

    fun getSubId() : String{
        return App.getInstance().resources.getStringArray(R.array.prices)[PreferencesProvider.priceIndex]
    }
}