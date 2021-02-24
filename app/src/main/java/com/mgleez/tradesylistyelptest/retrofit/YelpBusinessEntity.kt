package com.mgleez.tradesylistyelptest.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class YelpBusinessEntity(
  @SerializedName("rating")
  @Expose
  var rating: Double? = null,

  @SerializedName("price")
  @Expose
  var price: String? = null,

  @SerializedName("phone")
  @Expose
  var phone: String? = null,

  @SerializedName("id")
  @Expose
  var id: String,

  @SerializedName("alias")
  @Expose
  var alias: String? = null,

  @SerializedName("is_closed")
  @Expose
  var isClosed: Boolean? = null,

  @SerializedName("categories")
  @Expose
  var categories: List<YelpCategoryEntity>? = null,

  @SerializedName("review_count")
  @Expose
  var reviewCount: Int? = null,

  @SerializedName("name")
  @Expose
  var name: String,

  @SerializedName("url")
  @Expose
  var url: String? = null,

  @SerializedName("coordinates")
  @Expose
  var coordinates: CoordinatesEntity? = null,

  @SerializedName("image_url")
  @Expose
  var imageUrl: String? = null,

  @SerializedName("location")
  @Expose
  var location: YelpLocationEntity? = null,

  @SerializedName("distance")
  @Expose
  var distance: Double? = null,

  @SerializedName("transactions")
  @Expose
  var transactions: List<String>? = null
)