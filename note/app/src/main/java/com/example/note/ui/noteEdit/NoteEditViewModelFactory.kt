package com.example.note.ui.noteEdit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.note.database.dao.NoteDao
import com.example.note.ui.noteList.NoteListViewModel

class NoteEditViewModelFactory (
    private val datasource : NoteDao,
    private val application: Application
) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteEditViewModel::class.java)) {
            return NoteEditViewModel(datasource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}