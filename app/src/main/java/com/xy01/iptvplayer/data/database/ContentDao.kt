package com.xy01.iptvplayer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xy01.iptvplayer.data.model.Category
import com.xy01.iptvplayer.data.model.Channel
import com.xy01.iptvplayer.data.model.Movie
import com.xy01.iptvplayer.data.model.Series
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    
    // Categories
    @Query("SELECT * FROM categories WHERE profileId = :profileId AND type = :type")
    fun getCategoriesByType(profileId: String, type: String): Flow<List<Category>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)
    
    @Query("DELETE FROM categories WHERE profileId = :profileId AND type = :type")
    suspend fun deleteCategoriesByType(profileId: String, type: String)
    
    // Channels
    @Query("SELECT * FROM channels WHERE profileId = :profileId AND categoryId = :categoryId")
    fun getChannelsByCategory(profileId: String, categoryId: String): Flow<List<Channel>>
    
    @Query("SELECT * FROM channels WHERE profileId = :profileId")
    fun getAllChannels(profileId: String): Flow<List<Channel>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannels(channels: List<Channel>)
    
    @Query("DELETE FROM channels WHERE profileId = :profileId")
    suspend fun deleteChannelsByProfile(profileId: String)
    
    // Movies
    @Query("SELECT * FROM movies WHERE profileId = :profileId AND categoryId = :categoryId")
    fun getMoviesByCategory(profileId: String, categoryId: String): Flow<List<Movie>>
    
    @Query("SELECT * FROM movies WHERE profileId = :profileId")
    fun getAllMovies(profileId: String): Flow<List<Movie>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)
    
    @Query("DELETE FROM movies WHERE profileId = :profileId")
    suspend fun deleteMoviesByProfile(profileId: String)
    
    // Series
    @Query("SELECT * FROM series WHERE profileId = :profileId AND categoryId = :categoryId")
    fun getSeriesByCategory(profileId: String, categoryId: String): Flow<List<Series>>
    
    @Query("SELECT * FROM series WHERE profileId = :profileId")
    fun getAllSeries(profileId: String): Flow<List<Series>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: List<Series>)
    
    @Query("DELETE FROM series WHERE profileId = :profileId")
    suspend fun deleteSeriesByProfile(profileId: String)
}