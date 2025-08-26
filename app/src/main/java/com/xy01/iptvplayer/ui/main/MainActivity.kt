package com.xy01.iptvplayer.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.xy01.iptvplayer.R
import com.xy01.iptvplayer.databinding.ActivityMainBinding
import com.xy01.iptvplayer.ui.profile.ProfileActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as com.xy01.iptvplayer.IPTVApplication).database)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupBottomNavigation()
        observeViewModel()
        checkActiveProfile()
        
        // Set default fragment
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.navigation_live
        }
    }
    
    private fun setupUI() {
        binding.fabAddProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        
        binding.btnSync.setOnClickListener {
            viewModel.syncActiveProfileContent()
        }
    }
    
    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_live -> {
                    loadFragment(LiveFragment())
                    true
                }
                R.id.navigation_movies -> {
                    loadFragment(MoviesFragment())
                    true
                }
                R.id.navigation_series -> {
                    loadFragment(SeriesFragment())
                    true
                }
                else -> false
            }
        }
    }
    
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
    
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.activeProfile.collect { profile ->
                if (profile == null) {
                    // No active profile, show profile selection
                    showNoProfileState()
                } else {
                    // Show content for active profile
                    showContentState(profile.name)
                    viewModel.loadContentForProfile(profile.id)
                }
            }
        }
        
        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) 
                    android.view.View.VISIBLE else android.view.View.GONE
            }
        }
        
        lifecycleScope.launch {
            viewModel.errorMessage.collect { error ->
                error?.let {
                    showError(it)
                }
            }
        }
    }
    
    private fun checkActiveProfile() {
        lifecycleScope.launch {
            val activeProfile = viewModel.getActiveProfile()
            if (activeProfile == null) {
                startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
            }
        }
    }
    
    private fun showNoProfileState() {
        binding.textNoProfile.visibility = android.view.View.VISIBLE
        binding.contentContainer.visibility = android.view.View.GONE
    }
    
    private fun showContentState(profileName: String) {
        binding.textNoProfile.visibility = android.view.View.GONE
        binding.contentContainer.visibility = android.view.View.VISIBLE
        binding.textActiveProfile.text = "Active Profile: $profileName"
    }
    
    private fun showError(message: String) {
        // Show error message - could use Toast or Snackbar
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_LONG).show()
    }
}