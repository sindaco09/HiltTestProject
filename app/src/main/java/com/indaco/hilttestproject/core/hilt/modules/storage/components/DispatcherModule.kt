package com.indaco.hilttestproject.core.hilt.modules.storage.components

import com.indaco.hilttestproject.core.hilt.IODispatcher
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