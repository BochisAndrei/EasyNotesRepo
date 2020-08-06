package com.packg.easynotes.Elements

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "event_table")
class Event(@ColumnInfo(name = "date") val date : Date,
            @ColumnInfo(name = "text") val text : String) {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}