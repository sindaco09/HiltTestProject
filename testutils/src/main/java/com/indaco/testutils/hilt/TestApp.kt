package com.indaco.testutils.hilt

import android.app.Application

open class TestApp: Application()
//    Configuration.Provider
{

    companion object {
        lateinit var instance: TestApp

        fun analytics() {}
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

//    override fun getWorkManagerConfiguration() = Configuration.Builder()
//        .setExecutor(SynchronousExecutor())
//        .setWorkerFactory(WorkerFactory.getDefaultWorkerFactory())
//        .build()
}