package com.mgleez.tradesylistyelptest.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Mike Margulies 20210224
 */
@Dao
interface YelpBusinessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(yelpBusinessRoomEntity: YelpBusinessRoomEntity): Long

    @Query("SELECT * FROM businesses") // See: YelpCacheMapper
    suspend fun getBusinesses(): List<YelpBusinessRoomEntity>

    @Query("SELECT * FROM businesses WHERE id == :id") // See: YelpCacheMapper
    suspend fun getBusiness(id: String): YelpBusinessRoomEntity

}
