package com.packg.easynotes.Elements

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder_table")
class Folder(@ColumnInfo(name = "name") val name : String,
             @ColumnInfo(name = "image") val image : Int) : Element() {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}