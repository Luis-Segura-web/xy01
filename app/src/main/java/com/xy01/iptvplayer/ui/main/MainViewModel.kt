package com.xy01.iptvplayer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.xy01.iptvplayer.data.api.XtreamApiService
import com.xy01.iptvplayer.data.database.IPTVDatabase
import com.xy01.iptvplayer.data.model.Profile
import com.xy01.iptvplayer.data.repository.ContentRepository
import com.xy01.iptvplayer.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(database: IPTVDatabase) : ViewModel() {
    
    private val profileRepository = ProfileRepository(database.profileDao())
    private val contentRepository: ContentRepository
    
    private val _activeProfile = MutableStateFlow<Profile?>(null)
    val activeProfile: StateFlow<Profile?> = _activeProfile.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    init {
        // Initialize API service - will need actual implementation
        val retrofit = Retrofit.Builder()
            .baseUrl("http://placeholder.com/") // Temporary placeholder
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        val apiService = retrofit.create(XtreamApiService::class.java)
        contentRepository = ContentRepository(apiService, database.contentDao())
        
        loadActiveProfile()
    }
    
    private fun loadActiveProfile() {
        viewModelScope.launch {
            try {
                val profile = profileRepository.getActiveProfile()
                _activeProfile.value = profile
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
    
    suspend fun getActiveProfile(): Profile? {
        return profileRepository.getActiveProfile()
    }
    
    fun loadContentForProfile(profileId: String) {
        // This method will load cached content for UI
        // Implementation will depend on UI fragments
    }
    
    fun syncActiveProfileContent() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                val profile = _activeProfile.value
                if (profile != null) {
                    contentRepository.syncContentForProfile(profile)
                } else {
                    _errorMessage.value = "No active profile"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error syncing content"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class MainViewModelFactory(private val database: IPTVDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}