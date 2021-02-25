package com.mgleez.tradesylistyelptest.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Always update version number if database changes
 *
 * Created by Mike Margulies 20210224
 */
@Database(entities = [YelpBusinessRoomEntity::class, YelpReviewRoomEntity::class], version = 2)
abstract class YelpRoomDatabase : RoomDatabase() {
  abstract fun yelpBusinessDao() : YelpBusinessDao
  abstract fun yelpReviewDao() : YelpReviewDao

  companion object {
    const val DATABASE_NAME: String = "yelp_db"
  }
}