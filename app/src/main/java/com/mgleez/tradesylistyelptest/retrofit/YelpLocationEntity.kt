package com.mgleez.tradesylistyelptest.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class YelpLocationEntity (
  @SerializedName("city")
  @Expose
  var city: String? = null,

  @SerializedName("country")
  @Expose
  var country: String? = null,

  @SerializedName("address2")
  @Expose
  var address2: String? = null,

  @SerializedName("address3")
  @Expose
  var address3: String? = null,

  @SerializedName("state")
  @Expose
  var state: String? = null,

  @SerializedName("address1")
  @Expose
  var address1: String? = null,

  @SerializedName("zip_code")
  @Expose
  var zipCode: String? = null
)
