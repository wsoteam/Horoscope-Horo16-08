package com.wsoteam.horoscopes

import java.util.concurrent.TimeUnit

object Config {
    const val VPN_DATA_URL = "http://37.252.15.110/horo/"
    const val ID_PRICE = "no_ads_start"

    const val OPEN_PREM = "OPEN_PREM"
    const val OPEN_PREM_FROM_MAIN = "OPEN_PREM_FROM_MAIN"
    const val OPEN_PREM_FROM_REG = "OPEN_PREM_FROM_REG"

    const val OPEN_FROM_NOTIFY = "OPEN_FROM_NOTIFY"
    const val OPEN_FROM_EVENING_NOTIF = "OPEN_FROM_EVENING_NOTIF"

    const val ATTEMPTS_FOR_DAY = 3

    val MILLIS_FOR_NEW_ATTEMPTS = TimeUnit.HOURS.toMillis(24)
    val ADS_FREQUENCY = 50
    val PREM_SHOW_FREQUENCY = 10

    val DEF_HOUR_NOTIF = 9
    val DEF_MIN_NOTIF = 0

    val DEF_HOUR_EVENING_NOTIF = 18
    val DEF_MIN_EVENING_NOTIF = 0


    val IS_NEED_LOGGING = true
    val NEED_LOAD = true
    val DEFAULT_FREQUENCY_BANNER = 0

    //Stories
    val INDEX_SIGN = "INDEX_SIGN"
    val SIGN_DATA = "SIGN_DATA"


    const val ACTION_SHARE = "ACTION_SHARE"

}