package com.example.note.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "users", indices = [Index(value = ["username"], unique = true)])
data class User (
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "username")
    var username: String = "Placeholder Username",

    @ColumnInfo(name = "password")
    var password: String = "Placeholder Password"
)