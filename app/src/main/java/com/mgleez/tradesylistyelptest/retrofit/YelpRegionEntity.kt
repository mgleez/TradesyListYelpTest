package com.mgleez.tradesylistyelptest.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class YelpRegionEntity(
  @SerializedName("center")
  @Expose
  var center: CoordinatesEntity? = null
)
