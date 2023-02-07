package com.indaco.login.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.indaco.login.ui.screens.login.hilt_login.HiltLoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.indaco.hilttestproject.core.hilt.viewmodel.ViewModelFactory
import com.indaco.hilttestproject.core.hilt.viewmodel.ViewModelKey
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AccountModule {

    @Provides
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @IntoMap
    @ViewModelKey(HiltLoginViewModel::class)
    fun provideHiltLoginViewModel(viewmodel: HiltLoginViewModel): ViewModel = viewmodel
}