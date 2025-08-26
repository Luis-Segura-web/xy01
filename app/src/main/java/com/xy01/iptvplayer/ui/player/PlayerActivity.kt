package com.xy01.iptvplayer.ui.player

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.xy01.iptvplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    
    companion object {
        const val EXTRA_STREAM_URL = "stream_url"
        const val EXTRA_TITLE = "title"
    }
    
    private lateinit var binding: ActivityPlayerBinding
    private var player: ExoPlayer? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Keep screen on during playback
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Hide system UI for immersive experience
        hideSystemUI()
        
        val streamUrl = intent.getStringExtra(EXTRA_STREAM_URL)
        val title = intent.getStringExtra(EXTRA_TITLE)
        
        if (streamUrl != null) {
            initializePlayer(streamUrl)
        } else {
            finish()
        }
        
        binding.textTitle.text = title ?: "IPTV Player"
    }
    
    private fun initializePlayer(streamUrl: String) {
        player = ExoPlayer.Builder(this).build()
        
        binding.playerView.player = player
        binding.playerView.useController = true
        
        // Set up player listener
        player?.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                // Auto-hide controls when playing
                if (isPlaying) {
                    binding.playerView.hideController()
                }
            }
            
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                binding.progressBar.visibility = when (playbackState) {
                    Player.STATE_BUFFERING -> View.VISIBLE
                    else -> View.GONE
                }
            }
        })
        
        // Build the media item
        val mediaItem = MediaItem.fromUri(streamUrl)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.playWhenReady = true
    }
    
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
        )
    }
    
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        )
    }
    
    override fun onStart() {
        super.onStart()
        if (android.os.Build.VERSION.SDK_INT > 23) {
            initializePlayerIfNeeded()
        }
    }
    
    override fun onResume() {
        super.onResume()
        hideSystemUI()
        if (android.os.Build.VERSION.SDK_INT <= 23) {
            initializePlayerIfNeeded()
        }
    }
    
    override fun onPause() {
        super.onPause()
        if (android.os.Build.VERSION.SDK_INT <= 23) {
            releasePlayer()
        }
    }
    
    override fun onStop() {
        super.onStop()
        if (android.os.Build.VERSION.SDK_INT > 23) {
            releasePlayer()
        }
    }
    
    private fun initializePlayerIfNeeded() {
        if (player == null) {
            val streamUrl = intent.getStringExtra(EXTRA_STREAM_URL)
            if (streamUrl != null) {
                initializePlayer(streamUrl)
            }
        }
    }
    
    private fun releasePlayer() {
        player?.release()
        player = null
    }
    
    override fun onBackPressed() {
        showSystemUI()
        super.onBackPressed()
    }
}