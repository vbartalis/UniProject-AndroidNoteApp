package com.example.note.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note")
data class Note (
//    @PrimaryKey(autoGenerate = true)
//    var id: Long,

    @ColumnInfo(name = "userId")
    var userId: Long,

    @ColumnInfo(name = "user_note_title")
    var title: String,

    @ColumnInfo(name = "user_note_body")
    var body: String
):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}