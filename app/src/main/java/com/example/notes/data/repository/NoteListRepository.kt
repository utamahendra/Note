package com.example.notes.data.repository

import com.example.notes.data.local.NoteDatabase
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.domain.source.NoteListDataSource

class NoteListRepository(private val noteDatabase: NoteDatabase): NoteListDataSource {

    override suspend fun getNotes(): List<NoteEntity>? {
        return noteDatabase.noteDao().getNotes()
    }
}