package com.indaco.hilttestproject.ui.screens.onboarding

import android.widget.TextView
import com.indaco.hilttestproject.core.hilt.modules.storage.CacheModule
import com.indaco.hilttestproject.data.model.User
import com.indaco.hilttestproject.data.storage.cache.UserCache
import com.indaco.hilttestproject.ui.screens.onboarding.landing.LandingActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import com.indaco.hilttestproject.R
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@RunWith(RobolectricTestRunner::class)
@UninstallModules(CacheModule::class)
class LandingActivityRoboUnitTest {

    @BindValue
    @JvmField
    var mockUserCache: UserCache = mockk(relaxed = true)

    @Test
    fun verifyTestValueIsTrue() {
        every { mockUserCache.testValue } returns true
        Robolectric.buildActivity(LandingActivity::class.java).use { controller ->
            controller.setup() // Moves Activity to RESUMED state

            val activity = controller.get()
            val tv: TextView = activity.findViewById(R.id.test_value)
            assertTrue { tv.text == activity.getString(R.string.test_value_true) }
        }
    }

    @Test
    fun verifyTestValueIsFalse() {
        every { mockUserCache.testValue } returns false
        Robolectric.buildActivity(LandingActivity::class.java).use { controller ->
            controller.setup() // Moves Activity to RESUMED state

            val activity = controller.get()
            val tv: TextView = activity.findViewById(R.id.test_value)
            assertTrue { tv.text == activity.getString(R.string.test_value_false) }
            assertFalse { tv.text == activity.getString(R.string.test_value_true) }
        }
    }
}