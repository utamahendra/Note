package com.example.notes

import android.app.Application
import com.example.notes.di.appModule
import com.example.notes.di.repositoryModule
import com.example.notes.di.useCaseModule
import com.example.notes.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NoteApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@NoteApp)
            modules(
                arrayListOf(
                    appModule, viewModelModule, repositoryModule, useCaseModule
                )
            )
        }
    }
}