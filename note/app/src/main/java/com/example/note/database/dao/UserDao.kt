package com.example.note.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.note.database.entity.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Long) : User

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String) : User


    @Query("SELECT password FROM users WHERE username = :username")
    fun getPasswordByUsername(username: String) : String

    @Query("SELECT * FROM users")
    fun getAllUsers() : LiveData<List<User?>>

    @Query("DELETE FROM users")
    fun clearTable()
}