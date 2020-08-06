package com.packg.easynotes.Elements

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "text_note_table")
class TextNote(@ColumnInfo(name = "name") val name : String,
               @ColumnInfo(name = "text") val text : String) : Element() {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0

}