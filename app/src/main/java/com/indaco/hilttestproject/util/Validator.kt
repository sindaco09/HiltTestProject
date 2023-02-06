package com.indaco.hilttestproject.util

import android.text.TextUtils
import android.util.Log
import com.google.android.material.textfield.TextInputLayout
import com.indaco.hilttestproject.R

object Validator {

    enum class Type {EMAIL, PASSWORD}

    private const val PASSWORD_MIN = 3
    private const val PASSWORD_MAX = 10

    fun TextInputLayout.inputIsValid(type: Type): Boolean =
        when (type) {
            Type.EMAIL -> isEmailEntryValid()
            Type.PASSWORD -> isPasswordValid()
        }

    private fun TextInputLayout.isEmailEntryValid(): Boolean {
        val email = editText?.text.toString()
        Log.d("TAG","isEmailEntryValid: $email")

        val message =  when {
            email.isBlank() -> context.getString(R.string.error_email_blank)
            email.isEmailInvalid() -> context.getString(R.string.error_email_not_email_pattern)
            else ->  null
        }
        error = message
        return message == null
    }

    private fun String.isEmailInvalid(): Boolean =
        !android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun TextInputLayout.isPasswordValid(): Boolean {
        val password = editText?.text.toString()
        Log.d("TAG","isPasswordValid: $password")

        val message =  when {
            password.isBlank() -> context.getString(R.string.error_password_blank)
            password.length < PASSWORD_MIN -> context.getString(R.string.error_password_short)
            password.length > PASSWORD_MAX -> context.getString(R.string.error_password_long)
            password.any { !it.isLetterOrDigit() } -> context.getString(R.string.error_password_not_alphanumeric)
            else ->  null
        }

        error = message
        return message == null
    }

    fun TextInputLayout.matches(type: Type, otherField: String): Boolean {
        val field = editText?.text.toString()
        val fieldsMatch = field == otherField
        return if (fieldsMatch) {
            error = null
            true
        } else {
            error = when (type) {
                Type.PASSWORD -> context.getString(R.string.error_passwords_dont_match)
                Type.EMAIL -> context.getString(R.string.error_emails_dont_match)
            }
            false
        }
    }
}