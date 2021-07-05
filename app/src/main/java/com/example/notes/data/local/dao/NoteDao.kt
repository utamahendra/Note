package com.example.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notes.data.local.entity.NoteEntity

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(noteEntity: NoteEntity)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteDetail(id: Int): NoteEntity

    @Query("SELECT * FROM note")
    suspend fun getNotes(): List<NoteEntity>?

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)
}