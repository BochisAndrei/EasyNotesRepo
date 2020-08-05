package com.packg.easynotes.Elements

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "text_note_table")
class TextNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name : String,

    @ColumnInfo(name = "text")
    val text : String) : Element() {

}