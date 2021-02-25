package com.mgleez.tradesylistyelptest.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class YelpReviewListEntity(
  @SerializedName("reviews")
  @Expose
  var reviews: List<YelpReviewEntity>,

  @SerializedName("total")
  @Expose
  var total: Int,

  @SerializedName("possible_languages")
  @Expose
  var possible_languages: List<String>? = null
)