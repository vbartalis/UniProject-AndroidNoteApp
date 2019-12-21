package com.example.note.ui.register

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.note.database.dao.UserDao
import com.example.note.database.entity.User
import com.example.note.model.CurrentUser
import kotlinx.coroutines.*

class RegisterViewModel(
    val datasource: UserDao,
    application: Application
): ViewModel() {
    //The context we use to provide toast messages
    private var context: Context = application.applicationContext

    //Allows to cancel all coroutines started by this view, when the view is no longer used or destroyed
    private var viewModelJob = Job()

    //The scope determines what thread the coroutine will run on
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun register(username: String, password: String) : CurrentUser? {
        val user = User(0, username, password)

        Log.i("UserViewModel", "Created user's id is ${user.id}")

        val currentUser: CurrentUser? = runBlocking {
            insert(user)
        }
        if (currentUser != null) {
            Toast.makeText(context, "Successfully registered!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "User already exists!", Toast.LENGTH_SHORT).show()
        }
        return currentUser

    }

    private suspend fun insert(user: User) : CurrentUser? {
        Log.i("UserRepo", "Inserting new user " + user.username)
        return withContext(Dispatchers.IO) {
            try {
                runBlocking {
                    datasource.insertUser(user)
                    Log.i("UserViewModel", "Current user inserted")
                }

                createCurrentUser(user)
            } catch (e: SQLiteConstraintException) {
                null
            }
        }

    }

    private fun createCurrentUser(user : User) : CurrentUser {

        val dbUser = runBlocking { getUserByUsername(user.username) }

        return CurrentUser(dbUser.id, user.username)
    }

    private suspend fun getUserByUsername(username: String) : User {
        return withContext(Dispatchers.IO) {
            datasource.getUserByUsername(username)
        }
    }












    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
