package com.indaco.mylibrary.di.hilt.modules.storage

import android.content.SharedPreferences
import com.indaco.mylibrary.di.DebugAllOpen
import com.indaco.mylibrary.data.storage.cache.UserCache
import com.indaco.mylibrary.data.storage.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@DebugAllOpen
@Module
@InstallIn(SingletonComponent::class)
class CacheModule {

    @Provides
    @Singleton
    fun provideUserCache(userDao: UserDao, sp: SharedPreferences): UserCache =
        UserCache(userDao, sp)
}