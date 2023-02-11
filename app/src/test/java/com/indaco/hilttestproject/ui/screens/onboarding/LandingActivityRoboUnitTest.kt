package com.indaco.hilttestproject.ui.screens.onboarding

import android.widget.TextView
import com.indaco.hilttestproject.R
import com.indaco.hilttestproject.ui.screens.main.landing.LandingActivity
import com.indaco.mylibrary.data.storage.cache.UserCache
import com.indaco.mylibrary.di.hilt.modules.storage.CacheModule
import com.indaco.mylibrary.util.BaseString
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.mockk
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


@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
@LooperMode(LooperMode.Mode.PAUSED) //run the Robolectric tests on the main thread
@UninstallModules(CacheModule::class)
class LandingActivityRoboUnitTest {

    @BindValue
    @JvmField
    var mockUserCache: UserCache = mockk(relaxed = true)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun verifyTestValueIsTrue() {
        every { mockUserCache.testValue } returns true
        Robolectric.buildActivity(LandingActivity::class.java).use { controller ->
            controller.setup() // Moves Activity to RESUMED state

            val activity = controller.get()
            val tv: TextView = activity.findViewById(R.id.test_value)
            assertTrue { tv.text == activity.getString(BaseString.test_value_true) }
        }
    }

    @Test
    fun verifyTestValueIsFalse() {
        every { mockUserCache.testValue } returns false
        Robolectric.buildActivity(LandingActivity::class.java).use { controller ->
            controller.setup() // Moves Activity to RESUMED state

            val activity = controller.get()
            val tv: TextView = activity.findViewById(R.id.test_value)
            assertTrue { tv.text == activity.getString(BaseString.test_value_false) }
            assertFalse { tv.text == activity.getString(BaseString.test_value_true) }
        }
    }
}