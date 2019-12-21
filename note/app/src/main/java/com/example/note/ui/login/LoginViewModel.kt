package com.example.note.ui.login

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.note.database.dao.UserDao
import com.example.note.database.entity.User
import com.example.note.model.CurrentUser
import kotlinx.coroutines.*

class LoginViewModel(
    val datasource: UserDao,
    application: Application
): ViewModel() {
    //The context we use to provide toast messages
    private var context: Context = application.applicationContext

    //Allows to cancel all coroutines started by this view, when the view is no longer used or destroyed
    private var viewModelJob = Job()

    //The scope determines what thread the coroutine will run on
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun login(username : String, password : String) : CurrentUser? {

        val correctPassword = runBlocking {
            getPasswordByUsername(username)
        }

        val user : User = runBlocking {
            getUserByUsername(username)
        } ?: return null

        return if (correctPassword == password) {
            createCurrentUser(user)
        } else {
            null
        }
    }

    private suspend fun getPasswordByUsername(username: String) : String {
        return withContext(Dispatchers.IO) {
            datasource.getPasswordByUsername(username)
        }
    }

    private suspend fun getUserByUsername(username: String) : User {
        return withContext(Dispatchers.IO) {
            datasource.getUserByUsername(username)
        }
    }

    private fun createCurrentUser(user : User) : CurrentUser {

        val dbUser = runBlocking { getUserByUsername(user.username) }

        return CurrentUser(dbUser.id, user.username)
    }






    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
