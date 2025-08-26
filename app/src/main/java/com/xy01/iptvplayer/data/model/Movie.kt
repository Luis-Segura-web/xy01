package com.xy01.iptvplayer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    val streamId: String,
    val profileId: String,
    val name: String,
    val streamType: String,
    val streamIcon: String?,
    val rating: String?,
    val rating5based: Double?,
    val added: String?,
    val categoryId: String?,
    val containerExtension: String?,
    val customSid: String?,
    val directSource: String?,
    val cachedAt: Long = System.currentTimeMillis()
)