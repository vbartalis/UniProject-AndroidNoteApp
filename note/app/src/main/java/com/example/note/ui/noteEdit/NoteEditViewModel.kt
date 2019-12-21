package com.example.note.ui.noteEdit

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.note.database.dao.NoteDao
import com.example.note.database.entity.Note
import kotlinx.coroutines.*

class NoteEditViewModel(
    val datasource: NoteDao,
    application: Application
) : ViewModel() {
    var note: Note? = null
    lateinit var title : String
    lateinit var body: String

    //The context we use to provide toast messages
    private var context: Context = application.applicationContext

    //Allows to cancel all coroutines started by this view, when the view is no longer used or destroyed
    private var viewModelJob = Job()

    //The scope determines what thread the coroutine will run on
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun saveNote(title : String, body: String) {
        val mNote = Note(0, title, body)

        if (note == null) {
            insertNote(mNote)
            Toast.makeText(context, "Note Saved", Toast.LENGTH_SHORT).show()
        } else {
            mNote.id = note!!.id
            updateNote(mNote)
            Toast.makeText(context, "Note Updated", Toast.LENGTH_SHORT).show()
        }
    }


    fun updateNote(note: Note) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                runBlocking {
                    datasource.updateNote(note)
                }
            }
        }
    }

    fun insertNote(note: Note) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                runBlocking {
                    datasource.insertNote(note)
                }
            }
        }
    }


    fun deleteNote() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                runBlocking {
                    datasource.deleteNoteById(note?.id!!)
                }
            }
        }
    }






    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
