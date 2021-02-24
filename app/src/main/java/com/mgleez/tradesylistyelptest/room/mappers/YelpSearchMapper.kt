package com.mgleez.tradesylistyelptest.room.mappers

import com.mgleez.tradesylistyelptest.models.YelpBusiness
import com.mgleez.tradesylistyelptest.models.YelpSearch
import com.mgleez.tradesylistyelptest.retrofit.YelpBusinessEntity
import com.mgleez.tradesylistyelptest.retrofit.YelpSearchEntity
import com.mgleez.tradesylistyelptest.utils.EntityMapper
import javax.inject.Inject

/**
 * Class with injectable constructor to map
 * network YelpSearchEntity to/from UI used YelpSearch.
 *
 * Created by Mike Margulies 20210224
 */
class YelpSearchMapper
@Inject
constructor() :
  EntityMapper<YelpSearchEntity, YelpSearch> {
  override fun mapFromEntity(entity: YelpSearchEntity): YelpSearch = YelpSearch(
//        total = entity.total,
//        region = entity.region,
    yelpBusinessList = entity.businesses.map {
      YelpBusiness(
        id = it.id,
        name = it.name,
        image = it.imageUrl
//                review = it.review
      )
    }
  )

  override fun mapToEntity(domainModel: YelpSearch): YelpSearchEntity = YelpSearchEntity(
    total = domainModel.yelpBusinessList.size,
//        region = domainModel.region,
    businesses = domainModel.yelpBusinessList.map {
      YelpBusinessEntity(
        id = it.id,
        name = it.name,
        imageUrl = it.image
      )
    }
  )

}