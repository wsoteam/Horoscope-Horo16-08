package com.wsoteam.horoscopes.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

abstract class TimeInterval : Serializable {
    abstract var text : String
    abstract var matches : List<Int>
    abstract var ratings : List<Int>
}