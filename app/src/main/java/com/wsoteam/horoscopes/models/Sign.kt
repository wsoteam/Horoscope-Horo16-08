package com.wsoteam.horoscopes.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import java.io.Serializable


data class Sign(
    @SerializedName("today")
    var today: Today,
    @SerializedName("yesterday")
    var yesterday: Yesterday,
    @SerializedName("tomorrow")
    var tomorrow: Tomorrow,
    @SerializedName("week")
    var week: Week,
    @SerializedName("month")
    var month: Month,
    @SerializedName("year")
    var year: Year
) : Serializable {
}