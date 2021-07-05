package com.example.notes.presentation.noteeditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.common.viewstate.ViewState
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.domain.model.NoteEditorParams
import com.example.notes.domain.usecase.NoteEditorUseCase
import kotlinx.coroutines.launch

class NoteEditorViewModel(private val useCase: NoteEditorUseCase) : ViewModel() {

    private val _saveState = MutableLiveData<ViewState<Unit>>()
    private val _deleteNoteState = MutableLiveData<Unit>()
    private val _getNoteState = MutableLiveData<NoteEntity>()

    val saveNoteState: LiveData<ViewState<Unit>> = _saveState
    val deleteNoteState: LiveData<Unit> = _deleteNoteState
    val getNoteState: LiveData<NoteEntity> = _getNoteState

    fun saveNote(id: Int, title: String, content: String) {
        viewModelScope.launch {
            useCase.invoke(NoteEditorParams(id, title, content)).handleResult({
                _saveState.value = ViewState.Success(Unit)
            }, {
                _saveState.value = ViewState.Error(it)
            })
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            useCase.deleteNote(id)
            _deleteNoteState.value = Unit
        }
    }

    fun getNote(id: Int) {
        viewModelScope.launch {
            val result = useCase.getNote(id)
            _getNoteState.value = result
        }
    }
}