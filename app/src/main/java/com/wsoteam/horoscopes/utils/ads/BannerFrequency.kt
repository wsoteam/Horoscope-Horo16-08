package com.wsoteam.horoscopes.utils.ads

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlin.random.Random

object BannerFrequency {
    private var hasRequest = false

    fun runSetup() {
        if (!hasRequest && Config.NEED_LOAD) {
            hasRequest = true
            requestPercent {
                Log.e("LOL", it.toString()  +"dfg ")
                PreferencesProvider.banPercent = it
            }
        }
    }

    private fun requestPercent(onResult: (Int) -> Unit) {
        FirebaseDatabase.getInstance("https://astrohoro-fa6de-default-rtdb.firebaseio.com/")
            .reference
            .child("percent")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    onResult(Config.DEFAULT_FREQUENCY_BANNER)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val newPercent = p0.getValue(Int::class.java) ?: Config.DEFAULT_FREQUENCY_BANNER
                    onResult(newPercent)
                }
            })
    }

    fun needShow() : Boolean{
        return Random.nextInt(100) < PreferencesProvider.banPercent
    }
}