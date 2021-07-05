package com.example.notes.domain.source

import com.example.notes.data.local.entity.NoteEntity

interface NoteListDataSource {
    suspend fun getNotes(): List<NoteEntity>?
}