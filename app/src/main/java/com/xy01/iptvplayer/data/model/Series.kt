package com.xy01.iptvplayer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "series")
data class Series(
    @PrimaryKey
    val seriesId: String,
    val profileId: String,
    val name: String,
    val cover: String?,
    val plot: String?,
    val cast: String?,
    val director: String?,
    val genre: String?,
    val releaseDate: String?,
    val rating: String?,
    val rating5based: Double?,
    val categoryId: String?,
    val cachedAt: Long = System.currentTimeMillis()
)