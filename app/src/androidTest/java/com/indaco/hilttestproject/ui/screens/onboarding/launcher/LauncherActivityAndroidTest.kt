package com.indaco.hilttestproject.ui.screens.onboarding.launcher

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.indaco.hilttestproject.ui.screens.main.launcher.LauncherActivity
import com.indaco.hilttestproject.ui.screens.main.landing.LandingActivity
import com.indaco.testutils.hilt.lazyActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LauncherActivityAndroidTest {

    private val intent = Intent(ApplicationProvider.getApplicationContext(), LauncherActivity::class.java)

    @get: Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    // lazy is used because i need to record Intents via Intents.init() before the activity
    // is created. only other workaround is to start Intents.init() in init {} block
    @get: Rule(order = 1)
    val scenarioRule = lazyActivityScenarioRule<LauncherActivity>(launchActivity = false)

    private fun launchHiltActivityWithMocks() {
        hiltRule.inject()
        scenarioRule.launch(intent)
    }

    @Before
    fun setup() = Intents.init()

    @After
    fun tearDown() = Intents.release()

    @Test
    fun goesToLandingScreen() {
        // says to launch activity now with given intent
        launchHiltActivityWithMocks()

        intended(hasComponent(LandingActivity::class.java.name))
    }
}


// Below works for EmptyActivity, a non-hilt related activity.
// LauncherActivity navigates to LandingActivity now though. The below setup works for two
// non-hilt activities navigating from one to the other

//class LauncherActivityAndroidTest {
//
//    private val intent = Intent(ApplicationProvider.getApplicationContext(), LauncherActivity::class.java)
//
//    @get: Rule
//    val rule = lazyActivityScenarioRule<LauncherActivity>(launchActivity = false)
//
//    @Before
//    fun setup() {
//        Intents.init()
//    }
//
//    @After
//    fun teardown() {
//        Intents.release()
//    }
//
//    @Test
//    fun goesToLandingScreen() {
//        // says to launch activity now with given intent
//        rule.launch(intent)
//
//        intended(hasComponent(EmptyActivity::class.java.name))
//    }
//}