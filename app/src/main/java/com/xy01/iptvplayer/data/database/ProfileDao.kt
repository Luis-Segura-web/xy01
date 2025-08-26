package com.xy01.iptvplayer.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xy01.iptvplayer.data.model.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    
    @Query("SELECT * FROM profiles ORDER BY createdAt DESC")
    fun getAllProfiles(): Flow<List<Profile>>
    
    @Query("SELECT * FROM profiles WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveProfile(): Profile?
    
    @Query("SELECT * FROM profiles WHERE id = :id")
    suspend fun getProfileById(id: String): Profile?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile)
    
    @Update
    suspend fun updateProfile(profile: Profile)
    
    @Delete
    suspend fun deleteProfile(profile: Profile)
    
    @Query("UPDATE profiles SET isActive = 0")
    suspend fun deactivateAllProfiles()
    
    @Query("UPDATE profiles SET isActive = 1 WHERE id = :profileId")
    suspend fun setActiveProfile(profileId: String)
}