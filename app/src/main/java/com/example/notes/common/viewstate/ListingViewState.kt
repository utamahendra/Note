package com.example.notes.common.viewstate


sealed class ListingViewState<T> {
    data class Loading<T>(var progress: Float? = null) : ListingViewState<T>()
    data class Success<T>(var data: List<T>) : ListingViewState<T>()
    data class Empty<T>(var data: List<T>? = null) : ListingViewState<T>()
    data class Error<T>(var viewError: ViewError? = null) : ListingViewState<T>()
}