package com.emamagic.moviestreaming

import androidx.multidex.MultiDexApplication
import com.emamagic.moviestreaming.provider.conectivity.ConnectionLiveData
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MovieApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}