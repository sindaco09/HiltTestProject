package com.indaco.testutils_test.utils

import android.content.SharedPreferences
import io.mockk.MockKAdditionalAnswerScope
import io.mockk.every

object MockSharedPrefsUtil {

    fun SharedPreferences.getMockString(key: String, value: String?) =
        every { getString(key, any()) } returns value

    fun SharedPreferences.getMockInt(key: String, value: Int) =
        every { getInt(key, any()) } returns value

    fun SharedPreferences.getMockLong(key: String, value: Long) =
        every { getLong(key, any()) } returns value

    fun SharedPreferences.getMockBoolean(key: String, value: Boolean) =
        every { getBoolean(key, any()) } returns value
}