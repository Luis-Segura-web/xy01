package com.xy01.iptvplayer

import android.app.Application
import com.xy01.iptvplayer.data.database.IPTVDatabase

class IPTVApplication : Application() {
    
    val database by lazy { IPTVDatabase.getDatabase(this) }
    
    override fun onCreate() {
        super.onCreate()
    }
}