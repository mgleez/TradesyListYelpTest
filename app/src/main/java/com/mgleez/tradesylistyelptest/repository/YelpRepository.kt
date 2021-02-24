package com.mgleez.tradesylistyelptest.repository

import com.mgleez.tradesylistyelptest.getCurrentLatitude
import com.mgleez.tradesylistyelptest.getCurrentLongitude
import com.mgleez.tradesylistyelptest.getYelpApiKey
import com.mgleez.tradesylistyelptest.models.YelpBusiness
import com.mgleez.tradesylistyelptest.models.YelpSearch
import com.mgleez.tradesylistyelptest.retrofit.YelpRetrofit
import com.mgleez.tradesylistyelptest.retrofit.YelpSearchEntity
import com.mgleez.tradesylistyelptest.room.YelpBusinessDao
import com.mgleez.tradesylistyelptest.room.mappers.YelpBusinessCacheMapper
import com.mgleez.tradesylistyelptest.room.mappers.YelpBusinessMapper
import com.mgleez.tradesylistyelptest.utils.ViewModelIntent
import com.mgleez.tradesylistyelptest.utils.retrofitRoomMviFlow
import kotlinx.coroutines.flow.Flow

/**
 * Everything needed to map the successful result into the cache and retrieve data from the cache
 * into the domain objects, and a method to launch the request and emit loading, error and success
 * as viewModel intents. See: di/RepositoryModule.
 *
 * Created by Mike Margulies 20210224
 */
class YelpRepository
constructor(
  private val yelpBusinessDao: YelpBusinessDao,
  private val yelpRetrofit: YelpRetrofit,
  private val yelpBusinessMapper: YelpBusinessMapper,
  private val yelpBusinessCacheMapper: YelpBusinessCacheMapper
) {
  /**
   * A use-case to return a flow that will make a retrofit request, store it in the repository,
   * and as viewModel intents it will emit loading and emit error or success.
   * Used in: YelpViewModel's setYelpViewModelIntent()
   */
  suspend fun getYelpSearchIntentFlow(): Flow<ViewModelIntent<YelpSearch>> = retrofitRoomMviFlow(
    requestDataAndInsertResponseIntoRepository = {
      insertYelpBusinessIntoRepository(
        yelpRetrofit.getBusinessList(
          getYelpApiKey(),
          "Hollywood",
          getCurrentLatitude(),
          getCurrentLongitude()
        )
      )
    },
    retrieveResponseDataFromRepository = {
      getYelpBusinessFromRepository()
    }
  )

  /**
   * Get the Yelp data from the repository.
   */
  private suspend fun getYelpBusinessFromRepository() = YelpSearch(
    yelpBusinessCacheMapper.mapFromEntityList(yelpBusinessDao.getBusinesses())
  )

  /**
   * Using the yelpBusinessDao, insert into the repository the response Yelp data
   * (yelpBusiness list) mapped to cache data.
   */
  private suspend fun insertYelpBusinessIntoRepository(yelpSearchEntity: YelpSearchEntity) {
//        yelpBusinessDao.insert(
//            yelpBusinessCacheMapper.mapToEntity(
//                yelpBusinessMapper.mapFromEntity(
//                    yelpBusinessEntity
//                )
//            )
//        )
    val yelpBusinessList: List<YelpBusiness> =
      yelpBusinessMapper.mapFromEntityList(yelpSearchEntity.businesses)
    for (yelpBusiness in yelpBusinessList) {
      yelpBusinessDao.insert(yelpBusinessCacheMapper.mapToEntity(yelpBusiness))
    }
  }


}