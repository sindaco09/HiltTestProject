package com.indaco.testutils.utils

object Const {
    const val BLANK = ""
    const val EMAIL_VALID = "valid@gmail.com"
    const val EMAIL_INVALID = "validgmailcom"

    const val PASSWORD_SHORT = "aa"
    const val PASSWORD_LONG = "aaaaaaaaaaa"
    const val PASSWORD_INVALID = "p@ssw0r^|"
    const val PASSWORD_VALID = "Aaaaa1"
    val PASSWORDS = listOf(PASSWORD_SHORT, PASSWORD_LONG, PASSWORD_INVALID, PASSWORD_VALID, BLANK)
}