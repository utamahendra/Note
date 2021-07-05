package com.example.notes.common.extension

import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes

fun View.visible() {
    if (visibility != View.VISIBLE) visibility = View.VISIBLE
}

fun View.invisible() {
    if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
}

fun View.gone() {
    if (visibility != View.GONE) visibility = View.GONE
}

fun ViewGroup?.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this?.context).inflate(layoutRes, this, attachToRoot)
}

fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            afterTextChanged.invoke(p0.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

    })
}