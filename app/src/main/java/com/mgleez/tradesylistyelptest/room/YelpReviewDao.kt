package com.mgleez.tradesylistyelptest.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Mike Margulies 20210224
 */
@Dao
interface YelpReviewDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(yelpReviewRoomEntity: YelpReviewRoomEntity): Long

  @Insert()
  suspend fun insert(yelpReviewRoomEntityList: List<YelpReviewRoomEntity>): List<Long>

  @Query("SELECT * FROM reviews WHERE businessId == :businessId") // See: YelpReviewCacheMapper
  suspend fun getReviews(businessId: String): List<YelpReviewRoomEntity>

  @Query("SELECT * FROM reviews") // See: YelpCacheMapper
  suspend fun getReviews(): List<YelpReviewRoomEntity>

  @Query("SELECT * FROM reviews WHERE id == :id") // See: YelpReviewCacheMapper
  suspend fun getReview(id: String): YelpReviewRoomEntity

}
