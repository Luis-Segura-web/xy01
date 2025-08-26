package com.xy01.iptvplayer.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.xy01.iptvplayer.databinding.FragmentContentBinding
import kotlinx.coroutines.launch

class SeriesFragment : Fragment() {
    
    private var _binding: FragmentContentBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var seriesAdapter: SeriesAdapter
    
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
        seriesAdapter = SeriesAdapter { series ->
            // Start player with series stream or show episode selection
            // For now, just use the series ID as stream
            startPlayer(series.seriesId, series.name)
        }
        
        binding.recyclerContent.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = seriesAdapter
        }
    }
    
    private fun observeData() {
        lifecycleScope.launch {
            viewModel.activeProfile.collect { profile ->
                profile?.let { 
                    // Load series for active profile
                    // This would be implemented with actual data loading
                }
            }
        }
    }
    
    private fun startPlayer(streamId: String, title: String) {
        viewModel.activeProfile.value?.let { profile ->
            val streamUrl = com.xy01.iptvplayer.utils.StreamUrlBuilder.buildSeriesStreamUrl(profile, streamId)
            val intent = Intent(requireContext(), com.xy01.iptvplayer.ui.player.PlayerActivity::class.java).apply {
                putExtra(com.xy01.iptvplayer.ui.player.PlayerActivity.EXTRA_STREAM_URL, streamUrl)
                putExtra(com.xy01.iptvplayer.ui.player.PlayerActivity.EXTRA_TITLE, title)
            }
            startActivity(intent)
        }
    }
    
    private fun buildStreamUrl(streamId: String): String {
        return viewModel.activeProfile.value?.let { profile ->
            com.xy01.iptvplayer.utils.StreamUrlBuilder.buildSeriesStreamUrl(profile, streamId)
        } ?: "http://example.com/series/username/password/$streamId.mp4"
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}