package com.indaco.testutils.hilt

import android.app.Application
import android.content.Context
import androidx.annotation.Keep
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.CustomTestApplication

// This class is being used in gradle file, don't delete!
@Keep
@CustomTestApplication(TestApp::class)
class HiltTestRunner: AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestRunner_Application::class.java.name, context)
    }
}

// How Hilt Test Runner looks without a custom defined App

//class HiltTestRunner: AndroidJUnitRunner() {
//    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
//        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
//    }
//}