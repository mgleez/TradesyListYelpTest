package com.mgleez.tradesylistyelptest.di

import com.mgleez.tradesylistyelptest.repository.YelpRepository
import com.mgleez.tradesylistyelptest.retrofit.YelpRetrofit
import com.mgleez.tradesylistyelptest.retrofit.mappers.YelpReviewMapper
import com.mgleez.tradesylistyelptest.room.YelpBusinessDao
import com.mgleez.tradesylistyelptest.room.YelpReviewDao
import com.mgleez.tradesylistyelptest.room.mappers.YelpBusinessCacheMapper
import com.mgleez.tradesylistyelptest.room.mappers.YelpBusinessMapper
import com.mgleez.tradesylistyelptest.room.mappers.YelpReviewCacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Repository dependency injection object via dagger @Module made available application-wide via
 * hilt's @InstallIn using the SingletonComponent.
 *
 * Provides:
 * - YelpRepository from YelpBusinessDao, YelpRetrofit, and mappers.
 *
 * Created by Mike Margulies 20210224
 */
@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
  /**
   * Annotations create a single (java `@Singleton`) provider (dagger `@Provides`) method binding
   * the type YelpRepository to its returned value so dagger can inject where needed a
   * YelpRepository with all of these parameters.
   */
  @Singleton
  @Provides
  fun providesYelpRepository(
    yelpRetrofit: YelpRetrofit,
    yelpBusinessDao: YelpBusinessDao,
    yelpBusinessMapper: YelpBusinessMapper,
    yelpBusinessCacheMapper: YelpBusinessCacheMapper,
    yelpReviewDao: YelpReviewDao,
    yelpReviewMapper: YelpReviewMapper,
    yelpReviewCacheMapper: YelpReviewCacheMapper
  ): YelpRepository = YelpRepository(
    yelpRetrofit,
    yelpBusinessDao,
    yelpBusinessMapper,
    yelpBusinessCacheMapper,
    yelpReviewDao,
    yelpReviewMapper,
    yelpReviewCacheMapper
  )
}