package com.wsoteam.horoscopes.utils.loger

import android.util.Log
import com.wsoteam.horoscopes.Config

object L {
    fun log(log: String) {
        if (Config.IS_NEED_LOGGING) {
            Log.i("LOL", log)
        }
    }
}