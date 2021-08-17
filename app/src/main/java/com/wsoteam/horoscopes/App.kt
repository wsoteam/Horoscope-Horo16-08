package com.wsoteam.horoscopes

import android.os.Handler
import androidx.multidex.MultiDexApplication
import com.amplitude.api.Amplitude
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.qonversion.android.sdk.Qonversion
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.id.Creator

class App : MultiDexApplication() {

    @Volatile
    var applicationHandler: Handler? = null

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        SubscriptionProvider.init(this)


        Amplitude.getInstance()
            .initialize(this, getString(R.string.amplitude_id))
            .enableForegroundTracking(this)

        applicationHandler =  Handler(applicationContext.mainLooper)


        Qonversion.initialize(this, getString(R.string.qonversion_id), Creator.getId())
        //Smartlook.setupAndStartRecording(getString(R.string.smartlock_id))
        FacebookSdk.sdkInitialize(this)
        FacebookSdk.setAutoInitEnabled(true)
        FacebookSdk.setAutoLogAppEventsEnabled(true)
        AppEventsLogger.activateApp(applicationContext)
    }

    companion object {

        private lateinit var sInstance: App

        fun getInstance(): App {
            return sInstance
        }
    }
}