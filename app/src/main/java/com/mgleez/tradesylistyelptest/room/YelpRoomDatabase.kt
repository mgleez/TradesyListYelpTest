package com.mgleez.tradesylistyelptest.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Always update version number if database changes
 *
 * Created by Mike Margulies 20210224
 */
@Database(entities = [YelpBusinessRoomEntity::class], version = 1)
abstract class YelpRoomDatabase : RoomDatabase() {
  abstract fun yelpBusinessDao() : YelpBusinessDao

  companion object {
    const val DATABASE_NAME: String = "yelp_db"
  }
}