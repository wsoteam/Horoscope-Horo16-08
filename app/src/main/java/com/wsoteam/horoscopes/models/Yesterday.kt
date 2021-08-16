package com.wsoteam.horoscopes.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Yesterday(
    @SerializedName("text")
    override var text: String,
    @SerializedName("matches")
    override var matches: List<Int>,
    @SerializedName("ratings")
    override var ratings: List<Int>
) : Serializable, TimeInterval() {
}