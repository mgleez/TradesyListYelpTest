package com.mgleez.tradesylistyelptest.di

import com.mgleez.tradesylistyelptest.repository.YelpRepository
import com.mgleez.tradesylistyelptest.retrofit.YelpRetrofit
import com.mgleez.tradesylistyelptest.room.YelpBusinessDao
import com.mgleez.tradesylistyelptest.room.mappers.YelpBusinessCacheMapper
import com.mgleez.tradesylistyelptest.room.mappers.YelpBusinessMapper
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
object RepositoryModule  {

    /**
     * Annotations create a single (java `@Singleton`) provider (dagger `@Provides`) method binding
     * the type YelpRepository to its returned value so dagger can inject where needed a
     * YelpRepository with all of these parameters.
     */
    @Singleton
    @Provides
    fun providesYelpRepository(
      yelpBusinessDao: YelpBusinessDao,
      yelpRetrofit: YelpRetrofit,
      yelpBusinessMapper: YelpBusinessMapper,
      yelpBusinessCacheMapper: YelpBusinessCacheMapper
    ): YelpRepository = YelpRepository(
        yelpBusinessDao,
        yelpRetrofit,
        yelpBusinessMapper,
        yelpBusinessCacheMapper
    )

}