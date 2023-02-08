package com.indaco.mylibrary.di.hilt.modules.storage.components

import com.indaco.mylibrary.di.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
class DispatcherModule {

    @IODispatcher
    @Provides
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO
}