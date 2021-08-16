package com.wsoteam.horoscopes.utils.ads

import com.google.android.gms.ads.formats.UnifiedNativeAd
import java.util.ArrayList

interface NativeSpeaker {
    fun loadFin(nativeAd : ArrayList<UnifiedNativeAd>)
}