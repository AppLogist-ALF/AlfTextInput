package com.applogist.alf_text_input

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
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
    private var regexMessage: String? = null
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

        val ta = context.obtainStyledAttributes(attrs, R.styleable.AlfTextInput, 0, 0)

        try {
            text = ta.getText(R.styleable.AlfTextInput_text)
            hint = ta.getText(R.styleable.AlfTextInput_hint)
            helperText = ta.getText(R.styleable.AlfTextInput_helperText)
            placeHolderText = ta.getText(R.styleable.AlfTextInput_placeHolderText)
            titleText = ta.getText(R.styleable.AlfTextInput_titleText)
            inputType = ta.getInt(R.styleable.AlfTextInput_inputType, 0)
            regex = ta.getString(R.styleable.AlfTextInput_isRegex)
            regexMessage = ta.getString(R.styleable.AlfTextInput_regexMessage)
            textColor = ta.getColor(
                R.styleable.AlfTextInput_textColor, getThemeColor(android.R.attr.colorPrimary)
            )
            bgColor = ta.getColor(
                R.styleable.AlfTextInput_backgroundColor, inputLayout.boxBackgroundColor
            )

            borderColor = ta.getColor(
                R.styleable.AlfTextInput_borderColor, getThemeColor(android.R.attr.colorPrimary)
            )

            inputEditText.setText(text)
            inputEditText.hint = hint
            inputLayout.helperText = helperText
            inputLayout.hint = titleText
            inputLayout.placeholderText = placeHolderText
            inputEditText.setTextColor(textColor)
            inputLayout.boxBackgroundColor = bgColor
            inputLayout.boxStrokeColor = borderColor

            inputEditText.inputType = when (inputType) {
                InputType.TYPE_TEXT -> android.text.InputType.TYPE_CLASS_TEXT
                InputType.TYPE_PHONE -> android.text.InputType.TYPE_CLASS_PHONE
                InputType.TYPE_MAIL -> android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                InputType.TYPE_PASSWORD -> android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                InputType.TYPE_NUMBER -> android.text.InputType.TYPE_CLASS_NUMBER
                else -> android.text.InputType.TYPE_CLASS_TEXT
            }

            if (inputType == InputType.TYPE_PASSWORD)
                inputLayout.endIconMode = END_ICON_PASSWORD_TOGGLE

            inputEditText.doAfterTextChanged {
                regex?.let {
                    if (!isRegexValid(inputEditText.text.toString(), Regex(it))) {
                        inputLayout.error = regexMessage ?: "Regex uyumlu değil!"
                    } else {
                        inputLayout.error = null
                    }
                } ?: run {
                    when (inputType) {
                        InputType.TYPE_PHONE -> {
                            if (!isValidPhoneNumber(inputEditText.text.toString())) {
                                inputLayout.error = "Geçerli bir telefon numarası giriniz!"
                            } else {
                                inputLayout.error = null
                            }
                        }

                        InputType.TYPE_MAIL -> {
                            if (!isValidEmail(inputEditText.text.toString())) {
                                inputLayout.error = "Geçerli br mail adresi giriniz!"
                            } else {
                                inputLayout.error = null
                            }
                        }
                    }
                }
            }
        } finally {
            ta.recycle()
        }
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

    fun setRegexMessage(message: String) {
        this.regexMessage = message
    }

    fun getRegexMessage(): String? {
        return this.regexMessage
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

    fun setErrorMessage(message: String?) {
        inputLayout.error = message
    }


    private fun getThemeColor(colorId: Int): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(colorId, value, true)
        return value.data
    }

}