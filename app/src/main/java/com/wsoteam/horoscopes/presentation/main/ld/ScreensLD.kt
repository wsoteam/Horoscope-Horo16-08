package com.wsoteam.horoscopes.presentation.main.ld

import androidx.lifecycle.MutableLiveData

object ScreensLD {

    //Last element in times horoscopes, which need lock
    const val LOCK_INDEX = 5

    var screensLD = MutableLiveData<Int>()
}