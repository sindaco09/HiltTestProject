package com.indaco.login.di

import com.indaco.hilttestproject.core.hilt.dependencies.AppDependencies
import com.indaco.login.ui.screens.login.hilt_login.HiltLoginActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppDependencies::class],
    modules = [AccountModule::class]
)
interface AccountComponent {
    fun inject(activity: HiltLoginActivity)
}