package com.indaco.login.di

import android.app.Activity
import android.content.Context
import com.indaco.hilttestproject.core.hilt.dependencies.AppDependencies
import com.indaco.login.ui.screens.login.hilt_login.HiltLoginActivity
import dagger.hilt.android.EntryPointAccessors

object Injector {
    fun from(context: Context): AccountComponent {
        return DaggerAccountComponent
            .builder()
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    AppDependencies::class.java
                )
            )
            .build()
    }

    fun inject(activity: Activity) =
        with(activity) {
            when (this) {
                is HiltLoginActivity -> from(this).inject(this)
            }
        }
}