package com.xy01.iptvplayer.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.xy01.iptvplayer.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory((application as com.xy01.iptvplayer.IPTVApplication).database)
    }
    private lateinit var adapter: ProfileAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupRecyclerView()
        observeViewModel()
    }
    
    private fun setupUI() {
        binding.btnSave.setOnClickListener {
            saveProfile()
        }
        
        binding.btnCancel.setOnClickListener {
            finish()
        }
        
        // Support back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    
    private fun setupRecyclerView() {
        adapter = ProfileAdapter(
            onProfileClick = { profile ->
                viewModel.setActiveProfile(profile.id)
                finish()
            },
            onDeleteClick = { profile ->
                viewModel.deleteProfile(profile)
            }
        )
        
        binding.recyclerProfiles.layoutManager = LinearLayoutManager(this)
        binding.recyclerProfiles.adapter = adapter
    }
    
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.profiles.collect { profiles ->
                adapter.submitList(profiles)
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
                    Toast.makeText(this@ProfileActivity, it, Toast.LENGTH_LONG).show()
                }
            }
        }
        
        lifecycleScope.launch {
            viewModel.profileSaved.collect { saved ->
                if (saved) {
                    clearForm()
                    Toast.makeText(this@ProfileActivity, "Profile saved successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun saveProfile() {
        val name = binding.editProfileName.text.toString().trim()
        val serverUrl = binding.editServerUrl.text.toString().trim()
        val username = binding.editUsername.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()
        
        if (name.isEmpty() || serverUrl.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        viewModel.createProfile(name, serverUrl, username, password)
    }
    
    private fun clearForm() {
        binding.editProfileName.text.clear()
        binding.editServerUrl.text.clear()
        binding.editUsername.text.clear()
        binding.editPassword.text.clear()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}