package com.applogist.alftextinput

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AlfTextInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private lateinit var inputLayout: TextInputLayout
    private lateinit var inputEditText: TextInputEditText

    private var text: CharSequence? = null
    private var hint: CharSequence? = null
    private var helperText: CharSequence? = null
    private var regex: String? = null
    private var regexMessage: String? = null
    private var inputType: Int = 0
    private var textColor: Int = 0
    private var layoutColor: Int = 0

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.alf_text_input, this)
        inputLayout = findViewById(R.id.textInputLayout)
        inputEditText = findViewById(R.id.textInputEditText)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.ALEditText)

        try {
            text = ta.getText(R.styleable.ALEditText_text)
            hint = ta.getText(R.styleable.ALEditText_hint)
            helperText = ta.getText(R.styleable.ALEditText_helperText)
            inputType = ta.getInt(R.styleable.ALEditText_inputType, 0)
            regex = ta.getString(R.styleable.ALEditText_isRegex)
            regexMessage = ta.getString(R.styleable.ALEditText_regexMessage)
            textColor =
                ta.getColor(R.styleable.ALEditText_textColor, context.getColor(R.color.black))
            layoutColor =
                ta.getColor(
                    R.styleable.ALEditText_layoutColor,
                    context.getColor(R.color.purple_200)
                )

            inputEditText.setText(text)
            inputEditText.hint = hint
            inputLayout.helperText = helperText
            inputEditText.setTextColor(textColor)

            inputEditText.inputType = when (inputType) {
                InputType.TYPE_TEXT -> android.text.InputType.TYPE_CLASS_TEXT
                InputType.TYPE_PHONE -> android.text.InputType.TYPE_CLASS_PHONE
                InputType.TYPE_MAIL -> android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                InputType.TYPE_PASSWORD -> android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                InputType.TYPE_NUMBER -> android.text.InputType.TYPE_CLASS_NUMBER
                else -> android.text.InputType.TYPE_CLASS_TEXT
            }

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

    fun setHint(hint: String) {
        this.hint = hint
        inputEditText.hint = hint
    }

    fun setHelperText(helperText: String) {
        this.helperText = helperText
        inputLayout.helperText = helperText
    }

    fun setRegex(regex: String) {
        this.regex = regex
    }

    fun setRegexMessage(message: String) {
        regexMessage = message
    }

    fun setTextColor(color: Int) {
        this.textColor = color
        inputEditText.setTextColor(textColor)
    }

    fun setLayoutColor(color: Int) {
        this.layoutColor = color
        //setlaycolor
    }

    private fun isRegexValid(text: String, regex: Regex): Boolean {
        return text.matches(regex)
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPhoneNumber(number: String): Boolean {
        return Patterns.PHONE.matcher(number).matches()
    }


}