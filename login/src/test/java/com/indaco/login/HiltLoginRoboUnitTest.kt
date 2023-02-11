package com.indaco.login

import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.indaco.hilttestproject.R
import com.indaco.hilttestproject.ui.screens.main.landing.LandingActivity
import com.indaco.login.ui.screens.login.hilt_login.HiltLoginActivity
import com.indaco.mylibrary.util.BaseString
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import kotlin.test.assertFalse
import kotlin.test.assertTrue


// Robolectric doesn't support dynamic feature modules yet
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
@LooperMode(LooperMode.Mode.PAUSED) //run the Robolectric tests on the main thread
class HiltLoginRoboUnitTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun verifyTestValueIsTrue() {
        Robolectric.buildActivity(HiltLoginActivity::class.java).use { controller ->
            controller.setup() // Moves Activity to RESUMED state
            val activity = controller.get()
            val password = activity.findViewById<TextInputEditText>(R.id.password)

            assertTrue { password.hint == "Password" }
        }
    }
}