package com.xy01.iptvplayer.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.xy01.iptvplayer.databinding.FragmentContentBinding
import kotlinx.coroutines.launch

class LiveFragment : Fragment() {
    
    private var _binding: FragmentContentBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var channelAdapter: ChannelAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        observeData()
    }
    
    private fun setupRecyclerView() {
        channelAdapter = ChannelAdapter { channel ->
            // Start player with channel stream
            startPlayer(channel.streamId, channel.name)
        }
        
        binding.recyclerContent.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = channelAdapter
        }
    }
    
    private fun observeData() {
        lifecycleScope.launch {
            viewModel.activeProfile.collect { profile ->
                profile?.let { 
                    // Load channels for active profile
                    // This would be implemented with actual data loading
                }
            }
        }
    }
    
    private fun startPlayer(streamId: String, title: String) {
        val intent = Intent(requireContext(), com.xy01.iptvplayer.ui.player.PlayerActivity::class.java).apply {
            putExtra(com.xy01.iptvplayer.ui.player.PlayerActivity.EXTRA_STREAM_URL, buildStreamUrl(streamId))
            putExtra(com.xy01.iptvplayer.ui.player.PlayerActivity.EXTRA_TITLE, title)
        }
        startActivity(intent)
    }
    
    private fun buildStreamUrl(streamId: String): String {
        // Extract actual stream ID and build the streaming URL
        // This is a placeholder - in real implementation, we'd use the profile's server URL
        // and build the proper Xtream Codes streaming URL
        val actualStreamId = streamId.substringAfterLast("_")
        return "http://example.com/live/username/password/$actualStreamId.ts"
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}