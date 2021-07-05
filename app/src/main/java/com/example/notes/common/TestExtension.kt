package com.example.notes.common

import androidx.lifecycle.LiveData

fun <T> LiveData<T>.getTestObserver() = TestObserver<T>().apply {
    observeForever(this)
}