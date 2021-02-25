package com.mgleez.tradesylistyelptest.retrofit.mappers

import com.mgleez.tradesylistyelptest.models.YelpReview
import com.mgleez.tradesylistyelptest.retrofit.YelpReviewEntity
import com.mgleez.tradesylistyelptest.utils.EntityMapper
import javax.inject.Inject

/**
 * Class with injectable constructor to map
 * network YelpReviewEntity to/from UI-used YelpReview.
 *
 * Created by Mike Margulies 20210224
 */
class YelpReviewMapper
@Inject
constructor() :
  EntityMapper<YelpReviewEntity, YelpReview> {
  override fun mapFromEntity(entity: YelpReviewEntity) =
    YelpReview(
      id = entity.id,
      businessId = businessId,
      rating = entity.rating,
      review = entity.text
    )

  override fun mapToEntity(domainModel: YelpReview) =
    YelpReviewEntity(
      id = domainModel.id,
      rating = domainModel.rating,
      text = domainModel.review
    )
  var businessId = "YelpReviewMapper"
}