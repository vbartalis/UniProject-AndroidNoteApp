package com.example.note.ui.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.note.database.dao.UserDao
import javax.sql.DataSource

class RegisterViewModelFactory(
    private val datasource : UserDao,
    private val application: Application
) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(datasource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}