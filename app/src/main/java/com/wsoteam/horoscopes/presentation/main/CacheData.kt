package com.wsoteam.horoscopes.presentation.main

import com.wsoteam.horoscopes.models.Sign

object CacheData {
    private var signData : List<Sign>? = null
    private var iCachedData : ICachedData? = null

    fun setCachedData(signData : List<Sign>){
        this.signData = signData
        iCachedData?.cachedDataReady()
    }

    fun setObserver(iCachedData: ICachedData){
        this.iCachedData = iCachedData
    }

    fun removeObservers(){
        this.iCachedData = null
    }

    fun clearCache(){
        signData = null
    }

    fun getCachedData() : List<Sign>?{
        return signData
    }
}