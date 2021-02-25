package com.mgleez.tradesylistyelptest.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class YelpReviewEntity(
  @SerializedName("id")
  @Expose
  var id: String = "",

  @SerializedName("rating")
  @Expose
  var rating: Double = 0.0,

  @SerializedName("text")
  @Expose
  var text: String = "EMPTY"
)
