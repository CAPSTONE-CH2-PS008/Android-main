package com.ch2ps008.atomichabits.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.ch2ps008.atomichabits.R
import com.google.android.material.textfield.TextInputLayout

class PasswordEditText : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        // inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(character: CharSequence, start: Int, before: Int, count: Int) {
                val passwordLayout = textInputLayout()

                when {
                    character.toString().isEmpty() -> {
                        passwordLayout?.error = context.getString(R.string.password_empty)
                    }
                    character.toString().length < 6 -> {
                        passwordLayout?.error = context.getString(R.string.password_less)
                    }
                    else -> {
                        passwordLayout?.error = null
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun textInputLayout(): TextInputLayout? {
        var parent = parent
        while (parent != null) {
            if (parent is TextInputLayout) {
                return parent
            }
            parent = parent.parent
        }
        return null
    }
}