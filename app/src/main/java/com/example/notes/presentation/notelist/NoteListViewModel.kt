package com.example.notes.presentation.notelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.common.viewstate.ListingViewState
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.domain.usecase.NoteListUseCase
import kotlinx.coroutines.launch

class NoteListViewModel(private val useCase: NoteListUseCase) : ViewModel() {

    private val _noteListState = MutableLiveData<ListingViewState<NoteEntity>>()
    val noteListState: LiveData<ListingViewState<NoteEntity>> = _noteListState

    fun getNotes() {
        viewModelScope.launch {
            useCase.invoke(Unit).handleResult({ data ->
                _noteListState.value = ListingViewState.Success(data)
            }, {
                _noteListState.value = ListingViewState.Empty()
            })
        }
    }
}