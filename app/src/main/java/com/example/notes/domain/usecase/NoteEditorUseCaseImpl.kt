package com.example.notes.domain.usecase

import com.example.notes.common.Either
import com.example.notes.common.ValidateForm
import com.example.notes.common.viewstate.ViewError
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.domain.model.NoteEditorParams
import com.example.notes.domain.source.NoteEditorDataSource

class NoteEditorUseCaseImpl(private val repository: NoteEditorDataSource) : NoteEditorUseCase {

    override suspend fun invoke(params: NoteEditorParams): Either<Unit, ViewError> {
        return if (validateForm(params.title, params.content) == ValidateForm.FORM_VALID) {
            if (params.id == 0) {
                Either.Success(saveNote(params))
            } else {
                Either.Success(updateNote(params))
            }
        } else {
            Either.Fail(ViewError(validateForm(params.title, params.content)))
        }
    }

    private suspend fun saveNote(params: NoteEditorParams) {
        repository.saveNote(params.title, params.content)
    }

    private suspend fun updateNote(params: NoteEditorParams) {
        repository.updateNote(params.id, params.title, params.content)
    }

    override suspend fun getNote(id: Int): NoteEntity {
        return repository.getNote(id)
    }

    override suspend fun deleteNote(id: Int) {
        return repository.deleteNote(id)
    }

    private fun validateForm(title: String, content: String): String {
        return if (title.isBlank() && content.isBlank()) {
            ValidateForm.FORM_EMPTY
        } else if (title.isBlank()) {
            ValidateForm.TITLE_EMPTY
        } else if (content.isBlank()) {
            ValidateForm.CONTENT_EMPTY
        } else {
            ValidateForm.FORM_VALID
        }
    }
}