package com.example.android.capstoneproject_aroundtheworld.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class TripImagesUpdate(
        @ColumnInfo(name = "name")
        val tripName: String,
        @ColumnInfo(name = "images")
        val tripImages: List<String>
)