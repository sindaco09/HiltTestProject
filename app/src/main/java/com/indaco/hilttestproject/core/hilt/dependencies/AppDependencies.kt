package com.indaco.hilttestproject.core.hilt.dependencies

import android.content.SharedPreferences
import com.indaco.hilttestproject.core.hilt.IODispatcher
import com.indaco.hilttestproject.data.storage.dao.UserDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher


@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppDependencies {

    @IODispatcher
    fun dispatcher(): CoroutineDispatcher

    fun sharedPreferences(): SharedPreferences

    fun userDao(): UserDao
}