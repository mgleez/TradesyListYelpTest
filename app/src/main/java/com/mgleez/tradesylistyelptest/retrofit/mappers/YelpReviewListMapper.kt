package com.mgleez.tradesylistyelptest.retrofit.mappers

import com.mgleez.tradesylistyelptest.models.YelpReview
import com.mgleez.tradesylistyelptest.models.YelpReviewList
import com.mgleez.tradesylistyelptest.retrofit.YelpReviewEntity
import com.mgleez.tradesylistyelptest.retrofit.YelpReviewListEntity
import com.mgleez.tradesylistyelptest.utils.EntityMapper
import javax.inject.Inject

/**
 * Class with injectable constructor to map
 * network YelpReviewEntity to/from UI used YelpReview.
 *
 * Created by Mike Margulies 20210224
 */
class YelpReviewListMapper
@Inject
constructor() :
  EntityMapper<YelpReviewListEntity, YelpReviewList> {
  override fun mapFromEntity(entity: YelpReviewListEntity): YelpReviewList {
    return YelpReviewList(
      businessId = businessId,
      reviewList = entity.reviews.map {
        YelpReview(
          id = it.id,
          businessId = businessId,
          rating = it.rating,
          review = it.text
        )
      },
      businessList = mutableListOf()
    )
  }

  override fun mapToEntity(domainModel: YelpReviewList) = YelpReviewListEntity(
    total = domainModel.reviewList.size,
    reviews = domainModel.reviewList.map {
      YelpReviewEntity(
        id = it.id,
        rating = it.rating,
        text = it.review
      )
    }
  )
  var businessId = "YelpReviewListMapper"
}