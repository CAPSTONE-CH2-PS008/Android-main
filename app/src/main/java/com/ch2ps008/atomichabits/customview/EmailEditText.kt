package com.ch2ps008.atomichabits.customview

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import com.ch2ps008.atomichabits.R
import com.google.android.material.textfield.TextInputLayout

class EmailEditText : AppCompatEditText {
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
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(character: CharSequence, start: Int, before: Int, count: Int) {
                val emailLayout = textInputLayout()
                when {
                    character.toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(character.toString()).matches() -> {
                        emailLayout?.error = context.getString(R.string.wrong_email_format)
                        layoutReset(true)
                    }
                    else -> {
                        emailLayout?.error = null
                        layoutReset(false)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
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

    private fun layoutReset(isError: Boolean) {
        val emailLayout = textInputLayout()
        if (isError) {
            emailLayout?.isErrorEnabled = true
            emailLayout?.error = context.getString(R.string.wrong_email_format)
            val layoutParams = emailLayout?.layoutParams
            layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            emailLayout?.requestLayout()
        } else {
            emailLayout?.isErrorEnabled = false
            emailLayout?.error = null
        }
    }
}