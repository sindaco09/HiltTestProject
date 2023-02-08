package com.indaco.mylibrary.di.hilt.modules.storage.components

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.indaco.mylibrary.di.DebugAllOpen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@DebugAllOpen
@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

    companion object {
        const val PREFS_FILE = "MyPrefs"
        const val TEST_PREFS_FILE = "MyPrefsTest"
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
}