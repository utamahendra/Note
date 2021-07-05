package com.example.notes.domain.usecase

import com.example.notes.common.UseCase
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.domain.model.NoteEditorParams

interface NoteEditorUseCase : UseCase<NoteEditorParams, Unit> {

    suspend fun deleteNote(id: Int)

    suspend fun getNote(id: Int): NoteEntity

}