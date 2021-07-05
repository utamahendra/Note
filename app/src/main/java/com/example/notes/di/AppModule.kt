package com.example.notes.di

import androidx.room.Room
import com.example.notes.data.local.NoteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(androidContext(), NoteDatabase::class.java, "note-db")
            .build()
    }
}