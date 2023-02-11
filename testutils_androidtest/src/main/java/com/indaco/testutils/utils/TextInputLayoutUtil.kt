package com.indaco.testutils.utils

import android.view.View
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object TextInputLayoutUtil {

    fun withTextInputLayout(@LayoutRes id: Int): Matcher<View>? {
        return allOf(
            ViewMatchers.isDescendantOfA(ViewMatchers.withId(id)),
            ViewMatchers.isAssignableFrom(EditText::class.java)
        )
    }
}