package com.example.notes.data.repository

import com.example.notes.data.local.NoteDatabase
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.domain.source.NoteEditorDataSource

class NoteEditorRepository(private val noteDatabase: NoteDatabase) : NoteEditorDataSource {

    override suspend fun saveNote(title: String, content: String) {
        val request = NoteEntity(id = 0, title = title, content = content)
        noteDatabase.noteDao().insertNote(request)
    }

    override suspend fun deleteNote(id: Int) {
        noteDatabase.noteDao().deleteNote(id)
    }

    override suspend fun getNote(id: Int): NoteEntity {
        return noteDatabase.noteDao().getNoteDetail(id)
    }

    override suspend fun updateNote(id: Int, title: String, content: String) {
        val request = NoteEntity(id, title, content)
        noteDatabase.noteDao().updateNote(request)
    }
}