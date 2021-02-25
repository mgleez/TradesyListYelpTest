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

    @Insert()
    suspend fun insert(yelpBusinessRoomEntityList: List<YelpBusinessRoomEntity>): List<Long>

    @Query("SELECT id, name, rating, image, review FROM businesses") // See: YelpBusinessCacheMapper
    suspend fun getBusinesses(): List<YelpBusinessRoomEntity>

    @Query("SELECT * FROM businesses") // See: YelpCacheMapper
    suspend fun getBusinessesX(): List<YelpBusinessRoomEntity>

    @Query("SELECT * FROM businesses WHERE id == :id") // See: YelpBusinessCacheMapper
    suspend fun getBusiness(id: String): YelpBusinessRoomEntity

    @Query("DELETE FROM businesses")
    suspend fun deleteTable()
}