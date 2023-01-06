package com.applogist.alf_text_input

import android.text.TextUtils
import android.util.Patterns


fun isRegexValid(text: String, regex: Regex): Boolean {
    return text.matches(regex)
}

fun isValidEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPhoneNumber(number: String): Boolean {
    return Patterns.PHONE.matcher(number).matches()
}
