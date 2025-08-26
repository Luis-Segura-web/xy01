package com.xy01.iptvplayer.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.xy01.iptvplayer.data.model.Category
import com.xy01.iptvplayer.data.model.Channel
import com.xy01.iptvplayer.data.model.Movie
import com.xy01.iptvplayer.data.model.Profile
import com.xy01.iptvplayer.data.model.Series

@Database(
    entities = [Profile::class, Category::class, Channel::class, Movie::class, Series::class],
    version = 1,
    exportSchema = false
)
abstract class IPTVDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun contentDao(): ContentDao
    
    companion object {
        @Volatile
        private var INSTANCE: IPTVDatabase? = null
        
        fun getDatabase(context: Context): IPTVDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IPTVDatabase::class.java,
                    "iptv_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}