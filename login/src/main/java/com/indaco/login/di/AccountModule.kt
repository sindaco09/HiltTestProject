package com.indaco.login.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.indaco.login.ui.screens.login.hilt_login.HiltLoginViewModel
import com.indaco.mylibrary.di.hilt.viewmodel.ViewModelFactory
import com.indaco.mylibrary.di.hilt.viewmodel.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

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