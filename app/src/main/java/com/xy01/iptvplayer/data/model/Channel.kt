package com.xy01.iptvplayer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class Channel(
    @PrimaryKey
    val streamId: String,
    val profileId: String,
    val num: String,
    val name: String,
    val streamType: String,
    val streamIcon: String?,
    val epgChannelId: String?,
    val added: String?,
    val categoryId: String?,
    val customSid: String?,
    val tvArchive: Int?,
    val directSource: String?,
    val tvArchiveDuration: String?,
    val cachedAt: Long = System.currentTimeMillis()
)