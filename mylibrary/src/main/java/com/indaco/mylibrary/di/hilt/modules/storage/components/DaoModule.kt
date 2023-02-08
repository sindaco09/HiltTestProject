package com.indaco.mylibrary.di.hilt.modules.storage.components

import com.indaco.mylibrary.di.DebugAllOpen
import com.indaco.mylibrary.di.room.AppDatabase
import com.indaco.mylibrary.data.storage.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@DebugAllOpen
@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao =
        appDatabase.userDao()

}