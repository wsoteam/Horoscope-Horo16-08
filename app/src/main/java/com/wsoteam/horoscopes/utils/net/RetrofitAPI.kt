package com.wsoteam.horoscopes.utils.net

import com.wsoteam.horoscopes.models.Sign
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface RetrofitAPI {

    @GET("output.php")
    fun getData() : Deferred<List<Sign>>

    @GET("output.php?lang=pt")
    fun getPTData() : Deferred<List<Sign>>
}