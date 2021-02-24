package com.mgleez.tradesylistyelptest.di

import android.content.Context
import androidx.room.Room
import com.mgleez.tradesylistyelptest.room.YelpBusinessDao
import com.mgleez.tradesylistyelptest.room.YelpRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Room dependency injection object via dagger @Module made available application-wide via
 * hilt's @InstallIn using the SingletonComponent.
 *
 * Provides:
 * - YelpRoomDatabase from a Context
 * - YelpDao from a YelpRoomDatabase
 *
 * Created by Mike Margulies 20210224
 */
@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    /**
     * Annotations create a single (java) provider (dagger) method binding the type
     * YelpRoomDatabase to its returned value so dagger can inject a YelpRoomDatabase with
     * a context parameter where needed.
     */
    @Singleton
    @Provides
    fun providesYelpRoomDatabase(@ApplicationContext context: Context): YelpRoomDatabase =
        Room
            .databaseBuilder(
              context,
              YelpRoomDatabase::class.java,
              YelpRoomDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()

    /**
     * Annotations create a single (java) provider (dagger) method binding the type YelpDao
     * to its returned value so dagger can inject a YelpDao with a YelpRoomDatabase parameter
     * where needed.
     */
    @Singleton
    @Provides
    fun providesYelpDao(yelpRoomDatabase: YelpRoomDatabase): YelpBusinessDao =
        yelpRoomDatabase.yelpBusinessDao()

}