package com.xy01.iptvplayer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val categoryId: String,
    val profileId: String,
    val categoryName: String,
    val parentId: String?,
    val type: String, // "live", "movie", "series"
    val cachedAt: Long = System.currentTimeMillis()
)