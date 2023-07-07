package com.applogist.alf_text_input

import android.content.Context
import android.content.res.ColorStateList
import android.text.Editable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE

class AlfTextInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private lateinit var inputLayout: TextInputLayout
    private lateinit var inputEditText: TextInputEditText

    private var text: CharSequence? = null
    private var hint: CharSequence? = null
    private var helperText: CharSequence? = null
    private var placeHolderText: CharSequence? = null
    private var titleText: CharSequence? = null
    private var regex: String? = null
    private var enabled: Boolean = true
    private var errorMessage: String? = null
    private var inputType: Int = 0
    private var textColor: Int = 0
    private var bgColor: Int = 0
    private var borderColor: Int = 0

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.alf_text_input, this)
        inputLayout = findViewById(R.id.textInputLayout)
        inputEditText = findViewById(R.id.textInputEditText)

    }

    fun setText(text: String) {
        this.text = text
        inputEditText.setText(text)
    }

    fun getText(): Editable? {
        return inputEditText.text
    }

    fun setHint(hint: String) {
        this.hint = hint
        inputEditText.hint = hint
    }

    fun getHint(): CharSequence? {
        return inputEditText.hint
    }

    fun setHelperText(helperText: String) {
        this.helperText = helperText
        inputLayout.helperText = helperText
    }

    fun getHelperText(): CharSequence? {
        return inputLayout.helperText
    }

    fun setTitleText(title: String) {
        this.titleText = title
        inputLayout.hint = titleText
    }

    fun getTitleText(): CharSequence? {
        return inputLayout.hint
    }

    fun setPlaceHolderText(placeHolderText: String) {
        this.placeHolderText = placeHolderText
        inputLayout.placeholderText = placeHolderText
    }

    fun getPlaceHolderText(): CharSequence? {
        return inputLayout.placeholderText
    }

    fun setRegex(regex: String) {
        this.regex = regex
    }

    fun getRegex(): String? {
        return this.regex
    }

    fun setErrorMessage(message: String?) {
        this.errorMessage = message

        if (message.isNullOrEmpty()) {
            inputLayout.error = null
            inputLayout.isErrorEnabled = false
        } else {
            inputLayout.error = message
        }
    }

    fun getErrorMessage(): String? {
        return this.errorMessage
    }

    fun setTextColor(color: Int) {
        this.textColor = color
        inputEditText.setTextColor(textColor)
    }

    fun getTextColor(): Int {
        return inputEditText.currentTextColor
    }

    fun setBgColor(color: Int) {
        this.bgColor = color
        inputLayout.boxBackgroundColor = bgColor
    }

    fun getBgColor(): Int {
        return inputLayout.boxBackgroundColor
    }

    fun setBorderColor(color: Int) {
        this.borderColor = color
        inputLayout.boxStrokeColor = borderColor
    }

    fun getBorderColor(): Int {
        return inputLayout.boxStrokeColor
    }

    private fun getThemeColor(colorId: Int): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(colorId, value, true)
        return value.data
    }

}