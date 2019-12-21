package com.example.note.ui.noteList

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.note.database.dao.NoteDao

class NoteListViewModelFactory(
    private val datasource : NoteDao,
private val application: Application
) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteListViewModel::class.java)) {
            return NoteListViewModel(datasource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}