package com.mgleez.tradesylistyelptest.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Mike Margulies 20210224
 */
@Entity(tableName = "reviews")
data class YelpReviewRoomEntity(
  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "id")
  var id: String,

  @ColumnInfo(name = "businessId")
  var businessId: String,

  @ColumnInfo(name = "rating")
  var rating: Double,

  @ColumnInfo(name = "text")
  var text:   String

)
