package com.example.notes.domain.source

import com.example.notes.data.local.entity.NoteEntity

interface NoteEditorDataSource {

    suspend fun saveNote(title: String, content: String)

    suspend fun deleteNote(id: Int)

    suspend fun getNote(id: Int): NoteEntity

    suspend fun updateNote(id: Int, title: String, content: String)

}