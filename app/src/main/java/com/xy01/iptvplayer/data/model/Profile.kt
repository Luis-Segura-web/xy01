package com.xy01.iptvplayer.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "profiles")
data class Profile(
    @PrimaryKey
    val id: String,
    val name: String,
    val serverUrl: String,
    val username: String,
    val password: String,
    val isActive: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable