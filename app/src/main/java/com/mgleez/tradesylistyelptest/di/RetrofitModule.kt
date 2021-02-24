package com.mgleez.tradesylistyelptest.di

import com.mgleez.tradesylistyelptest.BASE_URL
import com.mgleez.tradesylistyelptest.retrofit.YelpRetrofit
import com.mgleez.tradesylistyelptest.utils.RetrofitModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Retrofit dependency injection object via dagger @Module made available application-wide via
 * hilt's @InstallIn using the SingletonComponent.
 *
 * Provides:
 * - GsonBuilder
 * - Retrofit.Builder from a Gson
 * - YelpService from a Retrofit.Builder
 *
 * Created by Mike Margulies 20210224
 */
@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    /**
     * Annotations create a single (java) provider (dagger) method binding the type YelpRetrofit
     * (interface for retrofit to get weather data) to its returned value so dagger can inject a
     * YelpService with a Retrofit.Builder parameter where needed.
     */
    @Singleton
    @Provides
    fun providesYelpService(retrofit: Retrofit): YelpRetrofit =
        retrofit.create(YelpRetrofit::class.java)

    /**
     * Annotations create a single (java) provider (dagger) method binding the type
     * RetrofitModule.BaseUrl to its returned value so dagger can inject a
     * RetrofitModule.BaseUrl where needed.
     */
    @Singleton
    @Provides
    fun providesBaseUrl(): RetrofitModule.BaseUrl =
        RetrofitModule.BaseUrl(BASE_URL)

}
