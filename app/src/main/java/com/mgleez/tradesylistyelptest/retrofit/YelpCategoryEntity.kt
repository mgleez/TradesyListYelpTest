package com.mgleez.tradesylistyelptest.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class YelpCategoryEntity (
  @SerializedName("alias")
  @Expose
  var alias: String? = null,

  @SerializedName("title")
  @Expose
  var title: String? = null
)