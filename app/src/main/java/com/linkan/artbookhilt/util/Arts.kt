package com.linkan.artbookhilt.util

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "art")
data class Arts(
    @ColumnInfo
    val artName : String,
    val artistName : String,
    val artYear : Int,
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null
)