package com.example.note.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.note.database.dao.UserDao
import com.example.note.ui.register.RegisterViewModel

class LoginViewModelFactory (
    private val datasource : UserDao,
    private val application: Application
) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(datasource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}