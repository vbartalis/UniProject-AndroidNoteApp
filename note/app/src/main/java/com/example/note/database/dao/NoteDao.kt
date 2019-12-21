package com.example.note.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.note.database.entity.Note

@Dao
interface NoteDao {

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM note WHERE id = :id")
    fun deleteNoteById(id: Long)

    @Query("SELECT * FROM note")
    fun getAllNote() : MutableList<Note>

    @Query("SELECT * FROM note where id = :id")
    fun getNoteById(id: Long) : Note

    @Query("SELECT note.id, note.userId, note.user_note_title, note.user_note_body FROM note INNER JOIN users ON (users.id = note.userId) WHERE username = :username")
    fun getNotesByUsername(username : String) : MutableList<Note>

    @Query("DELETE FROM note")
    fun clearTable()
}