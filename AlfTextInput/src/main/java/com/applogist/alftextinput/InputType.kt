package com.applogist.alftextinput

interface InputType {
    companion object {
        const val TYPE_TEXT = 0
        const val TYPE_PHONE = 1
        const val TYPE_MAIL = 2
        const val TYPE_NUMBER = 3
        const val TYPE_PASSWORD = 4
    }
}