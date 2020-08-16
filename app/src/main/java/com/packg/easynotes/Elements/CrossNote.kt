package com.packg.easynotes.Elements

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cross_note_table")
class CrossNote(@ColumnInfo(name = "name") val name : String) : Element() {

    //TODO You need to add arrayList of CheckBoxes
    @ColumnInfo(name = "cross_note_id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    @ColumnInfo(name = "trash")
    var trash : Boolean = false

    @ColumnInfo(name = "favorite")
    var favorite : Boolean = false
}