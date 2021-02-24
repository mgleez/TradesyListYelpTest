package com.mgleez.tradesylistyelptest.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoordinatesEntity(
    @SerializedName("latitude")
    @Expose
    var latitude: Double? = null,

    @SerializedName("longitude")
    @Expose
    var longitude: Double? = null
)
