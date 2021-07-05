package com.example.notes.di

import com.example.notes.domain.usecase.NoteEditorUseCase
import com.example.notes.domain.usecase.NoteEditorUseCaseImpl
import com.example.notes.domain.usecase.NoteListUseCase
import com.example.notes.domain.usecase.NoteListUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    factory { NoteListUseCaseImpl(get()) as NoteListUseCase }
    factory { NoteEditorUseCaseImpl(get()) as NoteEditorUseCase }
}