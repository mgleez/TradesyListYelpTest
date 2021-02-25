package com.mgleez.tradesylistyelptest.repository

import com.mgleez.tradesylistyelptest.*
import com.mgleez.tradesylistyelptest.models.YelpBusiness
import com.mgleez.tradesylistyelptest.models.YelpReview
import com.mgleez.tradesylistyelptest.models.YelpReviewList
import com.mgleez.tradesylistyelptest.models.YelpSearch
import com.mgleez.tradesylistyelptest.retrofit.YelpRetrofit
import com.mgleez.tradesylistyelptest.retrofit.YelpReviewListEntity
import com.mgleez.tradesylistyelptest.retrofit.YelpSearchEntity
import com.mgleez.tradesylistyelptest.retrofit.mappers.YelpReviewMapper
import com.mgleez.tradesylistyelptest.room.YelpBusinessDao
import com.mgleez.tradesylistyelptest.room.YelpReviewDao
import com.mgleez.tradesylistyelptest.room.mappers.YelpBusinessCacheMapper
import com.mgleez.tradesylistyelptest.room.mappers.YelpBusinessMapper
import com.mgleez.tradesylistyelptest.room.mappers.YelpReviewCacheMapper
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
  private val yelpRetrofit: YelpRetrofit,
  private val yelpBusinessDao: YelpBusinessDao,
  private val yelpBusinessMapper: YelpBusinessMapper,
  private val yelpBusinessCacheMapper: YelpBusinessCacheMapper,
  private val yelpReviewDao: YelpReviewDao,
  private val yelpReviewMapper: YelpReviewMapper,
  private val yelpReviewCacheMapper: YelpReviewCacheMapper
) {
    /**
     * A use-case to return a flow that will make a retrofit request, store it in the repository,
     * and as viewModel intents it will emit loading and emit error or success.
     * Used in: YelpSearchViewModel's setYelpViewModelIntent()
     */
    suspend fun getYelpSearchIntentFlow(term: String): Flow<ViewModelIntent<YelpSearch>> =
        retrofitRoomMviFlow(
          requestDataAndInsertResponseIntoRepository = {
            if (term != currentSearchTerm) {
              currentSearchTerm = term
              insertYelpSearchIntoRepository(
                currentLocation?.let { location ->
                  yelpRetrofit.getBusinessList(
                    getYelpApiKey(),
                    currentSearchTerm,
                    location
                  )
                } ?: run {
                  yelpRetrofit.getBusinessList(
                    getYelpApiKey(),
                    currentLatitude,
                    currentLongitude,
                    currentSearchTerm
                  )
                }
              )
            }
          },
          retrieveResponseDataFromRepository = {
            getYelpSearchFromRepository()
          }
        )

    /**
     * A use-case to return a flow that will make a retrofit request, store it in the repository,
     * and as viewModel intents it will emit loading and emit error or success.
     * Used in: YelpReviewListViewModel's setYelpViewModelIntent()
     */
    suspend fun getYelpReviewIntentFlow(businessId: String): Flow<ViewModelIntent<YelpReviewList>> =
        retrofitRoomMviFlow(
          requestDataAndInsertResponseIntoRepository = {
            insertYelpReviewListIntoRepository(
              businessId,
              yelpRetrofit.getReviewList(
                getYelpApiKey(),
                businessId
              )
            )
          },
          retrieveResponseDataFromRepository = {
            getYelpReviewListFromRepository(businessId)
          }
        )

    /**
     * Get the Yelp review data from the repository.
     */
    private suspend fun getYelpReviewListFromRepository(businessId: String): YelpReviewList =
        YelpReviewList(
          businessId = businessId,
          reviewList = yelpReviewCacheMapper.mapFromEntityList(yelpReviewDao.getReviews(businessId)) {
            it.also { it.businessId = businessId }
          },
          businessList = yelpBusinessCacheMapper.mapFromEntityList(yelpBusinessDao.getBusinesses())
        )

    /**
     * Using the yelpReviewDao, insert into the repository the response Yelp data
     * (yelpReview list) mapped to cache data.
     */
    private suspend fun insertYelpReviewListIntoRepository(
      businessId: String,
      yelpReviewListEntity: YelpReviewListEntity
    ) {
        yelpReviewMapper.businessId = businessId
        val yelpReviewList: List<YelpReview> =
            yelpReviewMapper.mapFromEntityList(yelpReviewListEntity.reviews)
        for (yelpReview in yelpReviewList) {
            yelpReviewDao.insert(yelpReviewCacheMapper.mapToEntity(yelpReview))
        }
        // Update the business's review text with first best review from review list.
        yelpBusinessDao.insert(yelpBusinessDao.getBusiness(businessId).apply {
          review = yelpReviewList.maxByOrNull { it.rating }?.review ?: ""
        })
    }

    /**
     * Get the Yelp data from the repository.
     */
    private suspend fun getYelpSearchFromRepository() = YelpSearch(
      yelpBusinessCacheMapper.mapFromEntityList(yelpBusinessDao.getBusinesses())
    )

    /**
     * Using the yelpBusinessDao, insert into the repository the response Yelp data
     * (yelpBusiness list) mapped to cache data.
     */
    private suspend fun insertYelpSearchIntoRepository(yelpSearchEntity: YelpSearchEntity) {
        yelpBusinessDao.deleteTable()
        val yelpBusinessList: List<YelpBusiness> =
            yelpBusinessMapper.mapFromEntityList(yelpSearchEntity.businesses)
        for (yelpBusiness in yelpBusinessList) {
            yelpBusinessDao.insert(yelpBusinessCacheMapper.mapToEntity(yelpBusiness))
        }
    }
}