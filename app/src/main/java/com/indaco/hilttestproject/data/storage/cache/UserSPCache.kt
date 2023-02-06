package com.indaco.hilttestproject.data.storage.cache

import android.content.SharedPreferences
import com.indaco.hilttestproject.core.hilt.DebugAllOpen
import javax.inject.Inject
import javax.inject.Singleton

@DebugAllOpen
@Singleton
class UserSPCache @Inject constructor(private val sp: SharedPreferences) {

    companion object {
        const val EMAIL = "email"
    }

    var email: String?
        get() = sp.getString(EMAIL, null)
        set(value) = sp.edit().putString(EMAIL,value).apply()

    var testValue: Boolean = true
}