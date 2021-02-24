package com.mgleez.tradesylistyelptest.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class YelpSearchEntity(
  @SerializedName("total")
  @Expose
  var total: Int,

  @SerializedName("businesses")
  @Expose
  var businesses: List<YelpBusinessEntity>,

  @SerializedName("region")
  @Expose
  var region: YelpRegionEntity? = null
)