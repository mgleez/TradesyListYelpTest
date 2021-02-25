package com.mgleez.tradesylistyelptest.models

/**
 * The data classes passed in the success viewModel intent for a business's reviews.
 *
 * Created by Mike Margulies 20210224
 */
data class YelpReviewList(
  var businessId: String,
  var reviewList: List<YelpReview>,
  var businessList: List<YelpBusiness>
)

data class YelpReview(
  var id: String,
  var businessId: String,
  var rating: Double,
  var review: String
)
