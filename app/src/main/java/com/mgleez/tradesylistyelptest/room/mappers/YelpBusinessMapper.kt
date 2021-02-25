package com.mgleez.tradesylistyelptest.room.mappers

import com.mgleez.tradesylistyelptest.models.YelpBusiness
import com.mgleez.tradesylistyelptest.retrofit.YelpBusinessEntity
import com.mgleez.tradesylistyelptest.utils.EntityMapper
import javax.inject.Inject

/**
 * Class with injectable constructor to map
 * network YelpBusinessEntity to/from UI-used YelpBusiness.
 *
 * Created by Mike Margulies 20210224
 */
class YelpBusinessMapper
@Inject
constructor() :
    EntityMapper<YelpBusinessEntity, YelpBusiness> {
    override fun mapFromEntity(entity: YelpBusinessEntity) = YelpBusiness(
        id = entity.id,
        name = entity.name,
        rating = entity.rating,
        image = entity.imageUrl,
        review = "..."
    )

    override fun mapToEntity(domainModel: YelpBusiness) = YelpBusinessEntity(
        id = domainModel.id,
        name = domainModel.name,
        rating = domainModel.rating,
        imageUrl = domainModel.image
        //review = domainModel.review
    )

}