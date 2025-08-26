package com.xy01.iptvplayer.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.xy01.iptvplayer.data.database.IPTVDatabase
import com.xy01.iptvplayer.data.model.Profile
import com.xy01.iptvplayer.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(database: IPTVDatabase) : ViewModel() {
    
    private val profileRepository = ProfileRepository(database.profileDao())
    
    val profiles = profileRepository.getAllProfiles()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    private val _profileSaved = MutableStateFlow(false)
    val profileSaved: StateFlow<Boolean> = _profileSaved.asStateFlow()
    
    fun createProfile(name: String, serverUrl: String, username: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _profileSaved.value = false
            
            try {
                profileRepository.createProfile(name, serverUrl, username, password)
                _profileSaved.value = true
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error creating profile"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun setActiveProfile(profileId: String) {
        viewModelScope.launch {
            try {
                profileRepository.setActiveProfile(profileId)
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error setting active profile"
            }
        }
    }
    
    fun deleteProfile(profile: Profile) {
        viewModelScope.launch {
            try {
                profileRepository.deleteProfile(profile)
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error deleting profile"
            }
        }
    }
}

class ProfileViewModelFactory(private val database: IPTVDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}