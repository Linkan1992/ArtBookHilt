package com.linkan.artbookhilt.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art(
    @ColumnInfo(name = "name")
    val artName : String,
    @ColumnInfo(name = "artistName")
    val artistName : String,
    @ColumnInfo(name = "year")
    val artYear : Int,
    @ColumnInfo(name = "imageUrl")
    val imageUrl : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null
)