package com.dasaevcompany.upax.utilities

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class VerifyField( private val verifyField : VerifyFieldListener ) {

    fun checkEmpty(editText: EditText, inputLayout: TextInputLayout, message: String) {
        editText.validate({ s -> s.notEmpty() }, message, inputLayout)
    }

    fun checkField(editText: EditText){
        editText.validateField()
    }

    private fun EditText.validate(
        validator: (String) -> Boolean,
        message: String,
        inputLayout: TextInputLayout
    ) {

        this.afterTextChanged {
            if (!validator(it)) {
                inputLayout.isErrorEnabled = true
                inputLayout.error = message
            } else {
                inputLayout.isErrorEnabled = false
            }
            verifyField.verifyField(true)
        }
    }

    private fun EditText.validateField(){
        this.afterTextChanged {
            verifyField.verifyField(true)
        }
    }

    private fun String.notEmpty():Boolean
            = this.isNotEmpty()

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object: TextWatcher {
            var originalText = ""
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged.invoke(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                originalText = s.toString()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}