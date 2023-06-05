package com.applogist.alftextinputdemo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.applogist.alf_text_input.AlfTextInput

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        findViewById<Button>(R.id.btn).setOnClickListener {
            val edittext = findViewById<AlfTextInput>(R.id.alfEditText)
            edittext.setErrorMessage(
                if (edittext.getText().isNullOrEmpty()) "error message" else null
            )

        }
    }
}