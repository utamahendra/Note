package com.example.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.data.local.dao.NoteDao
import com.example.notes.data.local.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

}