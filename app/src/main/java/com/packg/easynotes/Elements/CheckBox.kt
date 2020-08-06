package com.packg.easynotes.Elements

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "check_box_table")
class CheckBox (@ColumnInfo(name = "name") val name : String,
                @ColumnInfo(name = "checked") val checked : Int){

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}