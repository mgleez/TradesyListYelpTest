package com.mgleez.tradesylistyelptest.room.mappers

import com.mgleez.tradesylistyelptest.models.YelpReview
import com.mgleez.tradesylistyelptest.room.YelpReviewRoomEntity
import com.mgleez.tradesylistyelptest.utils.EntityMapper
import javax.inject.Inject

/**
 * Class with injectable constructor to map
 * network YelpReviewRoomEntity to/from UI-used YelpReview.
 *
 * Created by Mike Margulies 20210224
 */
class YelpReviewCacheMapper
@Inject
constructor() :
  EntityMapper<YelpReviewRoomEntity, YelpReview> {
  override fun mapFromEntity(entity: YelpReviewRoomEntity) =
    YelpReview(
      id = entity.id,
      businessId = entity.businessId,
      rating = entity.rating,
      review = entity.text,
    )

  override fun mapToEntity(domainModel: YelpReview) =
    YelpReviewRoomEntity(
      id = domainModel.id,
      businessId = domainModel.businessId,
      rating = domainModel.rating,
      text = domainModel.review,
    )
}
