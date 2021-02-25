package com.mgleez.tradesylistyelptest.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Mike Margulies 20210224
 */
@Entity(tableName = "businesses")
data class YelpBusinessRoomEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "rating")
    var rating: Double,

    @ColumnInfo(name = "image")
    var image: String? = "",

    @ColumnInfo(name = "review")
    var review: String = ""

)
