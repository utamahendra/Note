package com.example.notes.di

import com.example.notes.presentation.noteeditor.NoteEditorViewModel
import com.example.notes.presentation.notelist.NoteListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NoteListViewModel(get()) }
    viewModel { NoteEditorViewModel(get()) }
}