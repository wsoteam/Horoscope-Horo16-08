package com.wsoteam.horoscopes

import android.os.Handler
import androidx.multidex.MultiDexApplication
import com.amplitude.api.Amplitude
import com.bugfender.sdk.Bugfender
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.qonversion.android.sdk.Qonversion
import com.userexperior.UserExperior
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.id.Creator
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

class App : MultiDexApplication() {

    @Volatile
    var applicationHandler: Handler? = null

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        SubscriptionProvider.init(this)
        val config =
            YandexMetricaConfig.newConfigBuilder(getString(R.string.yam_id)).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
        if(BuildConfig.DEBUG) {
            //Bugsee.launch(this, "1187e351-e756-4bad-80af-5efa69a3ff56") //wadimkazak@mail.ru
            UserExperior.startRecording(getApplicationContext(), getString(R.string.debug_user_expirior_id));
        }else{
            UserExperior.startRecording(getApplicationContext(), getString(R.string.release_user_expirior_id));
        }
        Amplitude.getInstance()
            .initialize(this, getString(R.string.amplitude_id))
            .enableForegroundTracking(this)

        applicationHandler =  Handler(applicationContext.mainLooper)

        Bugfender.init(this, getString(R.string.fender_id), BuildConfig.DEBUG)
        Bugfender.enableCrashReporting()
        Bugfender.enableUIEventLogging(this)
        Bugfender.enableLogcatLogging() // optional, if you want logs automatically collected from logcat

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