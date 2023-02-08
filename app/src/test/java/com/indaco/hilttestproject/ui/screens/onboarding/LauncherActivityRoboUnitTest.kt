package com.indaco.hilttestproject.ui.screens.onboarding

import android.content.Intent
import com.indaco.hilttestproject.ui.screens.main.landing.LandingActivity
import com.indaco.hilttestproject.ui.screens.main.launcher.LauncherActivity
import com.indaco.hilttestproject.ui.screens.onboarding.login.LoginActivity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import kotlin.test.assertNotEquals


@RunWith(RobolectricTestRunner::class)
class LauncherActivityRoboUnitTest {

    private lateinit var launcherActivity: LauncherActivity

    @Before
    fun before() {
        launcherActivity = Robolectric.buildActivity(LauncherActivity::class.java)
            .create()
            .resume()
            .get()
    }

    @Test
    fun shouldStartLandingActivity() {
        val expectedIntent = Intent(launcherActivity, LandingActivity::class.java)
        val actual = shadowOf(launcherActivity.application).nextStartedActivity
        assertEquals(expectedIntent.component, actual.component)
    }

    // Another way, rather have lateinit var, is just build new activity in each test
    @Test
    fun shouldNotStartAnotherActivity() {
        Robolectric.buildActivity(LauncherActivity::class.java).use { controller ->

            controller.setup() // Moves Activity to RESUMED state

            val activity = controller.get()

            val expectedIntent = Intent(activity, LoginActivity::class.java)
            val actual = shadowOf(activity.application).nextStartedActivity
            assertNotEquals(expectedIntent.component, actual.component)
        }
    }
}