package com.example.note.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.note.database.dao.NoteDao
import com.example.note.database.dao.UserDao
import com.example.note.database.entity.Note
import com.example.note.database.entity.User

@Database(entities = [Note::class, User::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase(){
    abstract val userDao : UserDao
    abstract val noteDao : NoteDao

    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase {
            Log.i("Database", "Trying to create a database..")
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "todo_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    Log.i("Database", "New database instance created!")

                } else {
                    Log.i("Database", "Database already exists!")
                }
                return instance
            }
        }
    }
}