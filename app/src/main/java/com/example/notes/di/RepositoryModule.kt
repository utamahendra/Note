package com.example.notes.di

import com.example.notes.data.repository.NoteEditorRepository
import com.example.notes.data.repository.NoteListRepository
import com.example.notes.domain.source.NoteEditorDataSource
import com.example.notes.domain.source.NoteListDataSource
import org.koin.dsl.module

val repositoryModule = module {
    factory { NoteListRepository(get()) as NoteListDataSource }
    factory { NoteEditorRepository(get()) as NoteEditorDataSource }
}