package com.mgleez.tradesylistyelptest.room.mappers

import com.mgleez.tradesylistyelptest.models.YelpSearch
import com.mgleez.tradesylistyelptest.retrofit.YelpSearchEntity
import com.mgleez.tradesylistyelptest.utils.EntityMapper
import javax.inject.Inject

/**
 * Class with injectable constructor to map
 * network YelpSearchEntity to/from UI used YelpSearch.
 *
 * Created by Mike Margulies 20210224
 */
class YelpSearchCacheMapper
@Inject
constructor() :
  EntityMapper<YelpSearchEntity, YelpSearch> {
  override fun mapFromEntity(entity: YelpSearchEntity): YelpSearch = YelpSearch(
    //total = entity.total,
    //region = entity.region,
    yelpBusinessList = mutableListOf()
  )

  override fun mapToEntity(domainModel: YelpSearch): YelpSearchEntity = YelpSearchEntity(
    total = domainModel.yelpBusinessList.size,
    businesses = mutableListOf()
//        businesses = domainModel.yelpBusinessList
  )

}
