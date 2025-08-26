package com.xy01.iptvplayer.utils

import com.xy01.iptvplayer.data.model.Profile

object StreamUrlBuilder {
    
    fun buildLiveStreamUrl(profile: Profile, streamId: String): String {
        // Remove profile ID prefix to get actual stream ID
        val actualStreamId = streamId.substringAfterLast("_")
        
        // Build proper Xtream Codes live stream URL
        return "${profile.serverUrl}/live/${profile.username}/${profile.password}/$actualStreamId.ts"
    }
    
    fun buildMovieStreamUrl(profile: Profile, streamId: String): String {
        // Remove profile ID prefix to get actual stream ID
        val actualStreamId = streamId.substringAfterLast("_")
        
        // Build proper Xtream Codes movie stream URL
        return "${profile.serverUrl}/movie/${profile.username}/${profile.password}/$actualStreamId.mp4"
    }
    
    fun buildSeriesStreamUrl(profile: Profile, seriesId: String, episodeId: String? = null): String {
        // Remove profile ID prefix to get actual series ID
        val actualSeriesId = seriesId.substringAfterLast("_")
        
        // For series, we might need episode info - simplified version
        return "${profile.serverUrl}/series/${profile.username}/${profile.password}/$actualSeriesId.mp4"
    }
    
    fun buildServerUrl(baseUrl: String): String {
        // Ensure URL format is correct
        return if (baseUrl.startsWith("http://") || baseUrl.startsWith("https://")) {
            baseUrl.removeSuffix("/")
        } else {
            "http://$baseUrl".removeSuffix("/")
        }
    }
}