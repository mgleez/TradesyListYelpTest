package com.mgleez.tradesylistyelptest.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class YelpSearchEntity {
  @SerializedName("total")
  @Expose
  var total: Int? = null

  @SerializedName("businesses")
  @Expose
  var businesses: List<YelpBusinessEntity>? = null

  @SerializedName("region")
  @Expose
  var region: YelpRegionEntity? = null

}