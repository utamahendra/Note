package com.example.notes.common.viewstate


data class ViewError(
    val errorCode: String,
    val message: String? = null,
    val data: Any? = null
)