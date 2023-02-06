package com.indaco.hilttestproject.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthForm(
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = ""
): Parcelable {
    fun toUser(): User = User(email)
}