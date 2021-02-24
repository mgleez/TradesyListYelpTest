package com.mgleez.tradesylistyelptest.models

/**
 * The data class passed in the success viewModel intent.
 *
 * Created by Mike Margulies 20210224
 */
data class YelpSearch(
//    var yelpRegion: YelpRegion,
  var yelpBusinessList: List<YelpBusiness>
)

class YelpBusiness(
  var id: String,
  var name: String,
  var image: String?,
  var review: String? = null
)

class YelpRegion(
  var latitude: Double,
  var longitude: Double
)
