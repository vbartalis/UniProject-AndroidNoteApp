package com.example.note.ui.noteList

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.note.database.dao.NoteDao
import com.example.note.database.entity.Note
import kotlinx.coroutines.*

class NoteListViewModel (
    val datasource: NoteDao,
    application: Application
) : ViewModel() {
    //The context we use to provide toast messages
    private var context: Context = application.applicationContext

    //Allows to cancel all coroutines started by this view, when the view is no longer used or destroyed
    private var viewModelJob = Job()

    //The scope determines what thread the coroutine will run on
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //Livedata that holds all the tasksCounter created by the user
    private var _allNotes: MutableLiveData<MutableList<Note>> = MutableLiveData()
    val allNotes: LiveData<MutableList<Note>>
        get() = _allNotes


    fun reloadNotes() {
        uiScope.launch {
            _allNotes.value = runBlocking {
                withContext(Dispatchers.IO) {
                    datasource.getAllNote()
                }
            }
        }
    }

    // The String version of the current time
    val currentNotes = Transformations.map(allNotes) { notes ->
        var noteSize = 0
        for (note in notes) {
            noteSize++
        }

        "We have $noteSize notes"
    }



    init {
        reloadNotes()
    }



    private var _navigateToEdit = MutableLiveData<Note>()
    val navigateToEdit
    get() = _navigateToEdit

    fun onNoteClicked(note: Note) {
        _navigateToEdit.value = note
    }

    fun toEditNavigated() {
        _navigateToEdit.value = null
    }














    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
