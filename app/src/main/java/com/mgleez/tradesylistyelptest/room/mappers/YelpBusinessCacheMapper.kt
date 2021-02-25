package com.mgleez.tradesylistyelptest.room.mappers

import com.mgleez.tradesylistyelptest.models.YelpBusiness
import com.mgleez.tradesylistyelptest.room.YelpBusinessRoomEntity
import com.mgleez.tradesylistyelptest.utils.EntityMapper
import javax.inject.Inject

/**
 * Class with injectable constructor to map
 * network YelpBusinessRoomEntity to/from UI-used YelpBusiness.
 *
 * Created by Mike Margulies 20210224
 */
class YelpBusinessCacheMapper
@Inject
constructor() :
    EntityMapper<YelpBusinessRoomEntity, YelpBusiness> {
    override fun mapFromEntity(entity: YelpBusinessRoomEntity) =
        YelpBusiness(
            id = entity.id,
            name = entity.name,
            rating = entity.rating,
            image = entity.image,
            review = entity.review
        )

    override fun mapToEntity(domainModel: YelpBusiness) =
        YelpBusinessRoomEntity(
            id = domainModel.id,
            name = domainModel.name,
            rating = domainModel.rating ?: 0.0,
            image = domainModel.image,
            review = domainModel.review
        )

}
