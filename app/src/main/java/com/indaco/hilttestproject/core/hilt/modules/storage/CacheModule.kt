package com.indaco.hilttestproject.core.hilt.modules.storage

import android.content.SharedPreferences
import com.indaco.hilttestproject.core.hilt.DebugAllOpen
import com.indaco.hilttestproject.data.storage.cache.UserCache
import com.indaco.hilttestproject.data.storage.dao.UserDao
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