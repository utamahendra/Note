package com.example.notes.common.extension

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.notes.R
import org.jetbrains.anko.internals.AnkoInternals


fun Activity.slideInLeftTransition() {
    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
}

fun Activity.slideOutRightTransition() {
    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
}

inline fun <reified T : Activity> Activity.startActivityLeftTransition(vararg params: Pair<String, Any?>) {
    AnkoInternals.internalStartActivity(this, T::class.java, params)
    slideInLeftTransition()
}


fun Activity.startActivityForResultLeftTransition(intent: Intent, requestCode: Int) {
    startActivityForResult(intent, requestCode)
    slideInLeftTransition()
}