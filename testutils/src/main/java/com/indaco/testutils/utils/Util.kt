package com.indaco.testutils.utils

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice.*
import com.google.common.truth.Truth
import com.indaco.testutils.hilt.LazyActivityScenarioRule
import java.io.IOException

/**
 * Will have to figure out how to fix this later
 */
inline fun <reified T>verifyFragmentIsRunning(scenarioRule: LazyActivityScenarioRule<out AppCompatActivity>, @LayoutRes fragmentId: Int) {
    scenarioRule.getScenario().onActivity { activity ->
        activity.supportFragmentManager.findFragmentById(fragmentId)?.let {
            Truth.assertThat(it is T).isTrue()
        }
    }
}

/**
 * This method works like a charm
 *
 * SAMPLE CMD OUTPUT:
 * mShowRequested=true mShowExplicitlyRequested=true mShowForced=false mInputShown=true
 */
fun isKeyboardOpenedShellCheck(): Boolean {
    val checkKeyboardCmd = "dumpsys input_method | grep mInputShown"

    try {
        return getInstance(InstrumentationRegistry.getInstrumentation())
            .executeShellCommand(checkKeyboardCmd).contains("mInputShown=true")
    } catch (e: IOException) {
        throw RuntimeException("Keyboard check failed", e)
    }
}


val Int.view: ViewInteraction get() = Espresso.onView(ViewMatchers.withId(this))

fun Int.check(vararg vas: ViewAssertion) = vas.forEach { view.check(it) }

fun Int.withText(str: String) = view.check(matches(ViewMatchers.withText(str)))

fun Int.withVisibility(visibility: ViewMatchers.Visibility) = view.check(matches(withEffectiveVisibility(visibility)))

fun Int.perform(vararg viewActions: ViewAction) = viewActions.forEach { view.perform(it) }

fun Int.click() = view.perform(ViewActions.click())
