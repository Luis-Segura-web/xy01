package com.xy01.iptvplayer.data.repository

import com.xy01.iptvplayer.data.api.XtreamApiService
import com.xy01.iptvplayer.data.api.CategoryResponse
import com.xy01.iptvplayer.data.api.ChannelResponse
import com.xy01.iptvplayer.data.api.MovieResponse
import com.xy01.iptvplayer.data.api.SeriesResponse
import com.xy01.iptvplayer.data.database.ContentDao
import com.xy01.iptvplayer.data.model.Category
import com.xy01.iptvplayer.data.model.Channel
import com.xy01.iptvplayer.data.model.Movie
import com.xy01.iptvplayer.data.model.Profile
import com.xy01.iptvplayer.data.model.Series
import kotlinx.coroutines.flow.Flow

class ContentRepository(
    private val apiService: XtreamApiService,
    private val contentDao: ContentDao
) {
    
    suspend fun syncContentForProfile(profile: Profile) {
        try {
            // Sync categories
            syncCategories(profile, "live")
            syncCategories(profile, "movie")
            syncCategories(profile, "series")
            
            // Sync content
            syncChannels(profile)
            syncMovies(profile)
            syncSeries(profile)
        } catch (e: Exception) {
            throw e
        }
    }
    
    private suspend fun syncCategories(profile: Profile, type: String) {
        val response = when (type) {
            "live" -> apiService.getLiveCategories(profile.username, profile.password)
            "movie" -> apiService.getMovieCategories(profile.username, profile.password)
            "series" -> apiService.getSeriesCategories(profile.username, profile.password)
            else -> return
        }
        
        if (response.isSuccessful) {
            response.body()?.let { categoryResponses ->
                val categories = categoryResponses.map { categoryResponse ->
                    Category(
                        categoryId = "${profile.id}_${type}_${categoryResponse.category_id}",
                        profileId = profile.id,
                        categoryName = categoryResponse.category_name,
                        parentId = categoryResponse.parent_id,
                        type = type
                    )
                }
                
                contentDao.deleteCategoriesByType(profile.id, type)
                contentDao.insertCategories(categories)
            }
        }
    }
    
    private suspend fun syncChannels(profile: Profile) {
        val response = apiService.getLiveStreams(profile.username, profile.password)
        if (response.isSuccessful) {
            response.body()?.let { channelResponses ->
                val channels = channelResponses.map { channelResponse ->
                    Channel(
                        streamId = "${profile.id}_${channelResponse.stream_id}",
                        profileId = profile.id,
                        num = channelResponse.num,
                        name = channelResponse.name,
                        streamType = channelResponse.stream_type,
                        streamIcon = channelResponse.stream_icon,
                        epgChannelId = channelResponse.epg_channel_id,
                        added = channelResponse.added,
                        categoryId = channelResponse.category_id?.let { "${profile.id}_live_$it" },
                        customSid = channelResponse.custom_sid,
                        tvArchive = channelResponse.tv_archive,
                        directSource = channelResponse.direct_source,
                        tvArchiveDuration = channelResponse.tv_archive_duration
                    )
                }
                
                contentDao.deleteChannelsByProfile(profile.id)
                contentDao.insertChannels(channels)
            }
        }
    }
    
    private suspend fun syncMovies(profile: Profile) {
        val response = apiService.getVodStreams(profile.username, profile.password)
        if (response.isSuccessful) {
            response.body()?.let { movieResponses ->
                val movies = movieResponses.map { movieResponse ->
                    Movie(
                        streamId = "${profile.id}_${movieResponse.stream_id}",
                        profileId = profile.id,
                        name = movieResponse.name,
                        streamType = movieResponse.stream_type,
                        streamIcon = movieResponse.stream_icon,
                        rating = movieResponse.rating,
                        rating5based = movieResponse.rating_5based,
                        added = movieResponse.added,
                        categoryId = movieResponse.category_id?.let { "${profile.id}_movie_$it" },
                        containerExtension = movieResponse.container_extension,
                        customSid = movieResponse.custom_sid,
                        directSource = movieResponse.direct_source
                    )
                }
                
                contentDao.deleteMoviesByProfile(profile.id)
                contentDao.insertMovies(movies)
            }
        }
    }
    
    private suspend fun syncSeries(profile: Profile) {
        val response = apiService.getSeries(profile.username, profile.password)
        if (response.isSuccessful) {
            response.body()?.let { seriesResponses ->
                val series = seriesResponses.map { seriesResponse ->
                    Series(
                        seriesId = "${profile.id}_${seriesResponse.series_id}",
                        profileId = profile.id,
                        name = seriesResponse.name,
                        cover = seriesResponse.cover,
                        plot = seriesResponse.plot,
                        cast = seriesResponse.cast,
                        director = seriesResponse.director,
                        genre = seriesResponse.genre,
                        releaseDate = seriesResponse.releaseDate,
                        rating = seriesResponse.rating,
                        rating5based = seriesResponse.rating_5based,
                        categoryId = seriesResponse.category_id?.let { "${profile.id}_series_$it" }
                    )
                }
                
                contentDao.deleteSeriesByProfile(profile.id)
                contentDao.insertSeries(series)
            }
        }
    }
    
    // Getter methods for UI
    fun getCategoriesByType(profileId: String, type: String): Flow<List<Category>> {
        return contentDao.getCategoriesByType(profileId, type)
    }
    
    fun getChannelsByCategory(profileId: String, categoryId: String): Flow<List<Channel>> {
        return contentDao.getChannelsByCategory(profileId, categoryId)
    }
    
    fun getAllChannels(profileId: String): Flow<List<Channel>> {
        return contentDao.getAllChannels(profileId)
    }
    
    fun getMoviesByCategory(profileId: String, categoryId: String): Flow<List<Movie>> {
        return contentDao.getMoviesByCategory(profileId, categoryId)
    }
    
    fun getAllMovies(profileId: String): Flow<List<Movie>> {
        return contentDao.getAllMovies(profileId)
    }
    
    fun getSeriesByCategory(profileId: String, categoryId: String): Flow<List<Series>> {
        return contentDao.getSeriesByCategory(profileId, categoryId)
    }
    
    fun getAllSeries(profileId: String): Flow<List<Series>> {
        return contentDao.getAllSeries(profileId)
    }
}