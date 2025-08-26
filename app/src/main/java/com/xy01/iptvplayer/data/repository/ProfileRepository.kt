package com.xy01.iptvplayer.data.repository

import com.xy01.iptvplayer.data.database.ProfileDao
import com.xy01.iptvplayer.data.model.Profile
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) {
    
    fun getAllProfiles(): Flow<List<Profile>> {
        return profileDao.getAllProfiles()
    }
    
    suspend fun getActiveProfile(): Profile? {
        return profileDao.getActiveProfile()
    }
    
    suspend fun getProfileById(id: String): Profile? {
        return profileDao.getProfileById(id)
    }
    
    suspend fun insertProfile(profile: Profile) {
        profileDao.insertProfile(profile)
    }
    
    suspend fun createProfile(name: String, serverUrl: String, username: String, password: String): String {
        val profileId = UUID.randomUUID().toString()
        val formattedUrl = com.xy01.iptvplayer.utils.StreamUrlBuilder.buildServerUrl(serverUrl)
        val profile = Profile(
            id = profileId,
            name = name,
            serverUrl = formattedUrl,
            username = username,
            password = password
        )
        profileDao.insertProfile(profile)
        return profileId
    }
    
    suspend fun updateProfile(profile: Profile) {
        profileDao.updateProfile(profile)
    }
    
    suspend fun deleteProfile(profile: Profile) {
        profileDao.deleteProfile(profile)
    }
    
    suspend fun setActiveProfile(profileId: String) {
        profileDao.deactivateAllProfiles()
        profileDao.setActiveProfile(profileId)
    }
}