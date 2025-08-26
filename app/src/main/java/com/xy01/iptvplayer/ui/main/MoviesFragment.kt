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

class MoviesFragment : Fragment() {
    
    private var _binding: FragmentContentBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var movieAdapter: MovieAdapter
    
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
        movieAdapter = MovieAdapter { movie ->
            // Start player with movie stream
            startPlayer(movie.streamId, movie.name)
        }
        
        binding.recyclerContent.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter
        }
    }
    
    private fun observeData() {
        lifecycleScope.launch {
            viewModel.activeProfile.collect { profile ->
                profile?.let { 
                    // Load movies for active profile
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
        // Extract actual stream ID and build the streaming URL for movies
        val actualStreamId = streamId.substringAfterLast("_")
        return "http://example.com/movie/username/password/$actualStreamId.mp4"
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}