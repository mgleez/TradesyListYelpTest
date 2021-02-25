package com.mgleez.tradesylistyelptest.retrofit

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface for retrofit to get yelp data.
 *
 * Created by Mike Margulies 20210224
 */
interface YelpRetrofit {
  /**
   * Using retrofit2's @GET annotation with @Query annotation's URL param appending,
   * requests a search for businesses by search term, latitude, longitude, etc.
   */
  @GET("businesses/search")
  suspend fun getBusinessList(
    @Header("Authorization") key: String,
    @Query("latitude") latitude: Double,
    @Query("longitude") longitude: Double,
    @Query("term") term: String
//    @Query("latitude") latitude: Double,
//    @Query("longitude") longitude: Double,
//    @Query("term") term: String,
//    @Query("location") location: String? = null,
//    @Query("radius") radius: Int? = null,
//    @Query("categories") categories: String? = null,
//    @Query("locale") locale: String? = null,
//    @Query("limit") limit: Int? = null,
//    @Query("offset") offset: Int? = null,
//    @Query("sort_by") sortBy: String? = null,
//    @Query("price") price: String? = null,
//    @Query("open_now") open_now: Boolean? = null,
//    @Query("open_at") open_at: Int? = null,
//    @Query("attributes") attributes: String? = null
  ): YelpSearchEntity

  /**
   * Using retrofit2's @GET annotation with @Query annotation's URL param appending
   * request a search for businesses by search term, location.
   */
  @GET("businesses/search")
  suspend fun getBusinessList(
    @Header("Authorization") key: String,
    @Query("term") term: String,
    @Query("location") location: String
  ): YelpSearchEntity

  /**
   * Using retrofit2's @GET annotation with @Path annotation's URL param inserting
   * request a search for businesses reviews by business id.
   */
  @GET("businesses/{id}/reviews")
  suspend fun getReviewList(
    @Header("Authorization") key: String,
    @Path("id") id: String
  ): YelpReviewListEntity

}