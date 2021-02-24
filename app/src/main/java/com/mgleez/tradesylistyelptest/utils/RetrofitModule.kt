package com.mgleez.tradesylistyelptest.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Retrofit dependency injection object, for utility providers, via dagger @Module made available application-wide via
 * hilt's @InstallIn using the SingletonComponent.
 *
 * Provides:
 * - Gson
 * - GsonConverterFactory from a Gson
 * - HttpLoggingInterceptor
 * - OkHttpClient from a HttpLoggingInterceptor
 * - Retrofit from GsonConverterFactory and OkHttpClient. REQUIRES fun in app's RetrofitModule:
 *
 * fun providesBaseUrl(): RetrofitModule.BaseUrl = RetrofitModule.BaseUrl(BASE_URL)
 *
 * Created by Mike Margulies 20210224
 */
@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

  /**
   * Annotations create a single (java) provider (dagger) method binding the type Gson to its
   * returned value so dagger can inject a Gson where needed.
   */
  @Singleton
  @Provides
  fun providesGson(): Gson = GsonBuilder()
    .excludeFieldsWithoutExposeAnnotation()
    .create()

  /**
   * Annotations create a single (java) provider (dagger) method binding the type
   * GsonConverterFactory to its returned value so dagger can inject a GsonConverterFactory with a
   * Gson parameter where needed.
   */
  @Singleton
  @Provides
  fun providesGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory
    .create(gson)

  @Provides
  @Singleton
  fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
  }

  @Provides
  @Singleton
  fun providesOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .callTimeout(60, TimeUnit.SECONDS)
    .connectTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .build()

  /**
   * Annotations create a single (java) provider (dagger) method binding the type Retrofit
   * to its returned value so dagger can inject a
   * Retrofit with GsonConverterFactory and OkHttpClient parameters where needed.
   */
  @Singleton
  @Provides
  fun providesRetrofit(
    baseUrl: BaseUrl,
    gsonConverterFactory: GsonConverterFactory,
    okHttpClient: OkHttpClient
  ): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl.url)
    .addConverterFactory(gsonConverterFactory)
    .client(okHttpClient)
    .build()

  data class BaseUrl(val url: String)

}
