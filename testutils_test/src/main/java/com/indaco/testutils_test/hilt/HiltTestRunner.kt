package com.indaco.testutils_test.hilt

import android.app.Application
import android.content.Context
import androidx.annotation.Keep
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

// This class is being used in gradle file, don't delete!
@Keep
class HiltTestRunner: AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}