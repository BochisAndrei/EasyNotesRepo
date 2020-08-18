package com.packg.easynotes.Elements

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
abstract class Element {
    /**
     * Indicates when the [Element] was created or when it was edited.
     */
    @ColumnInfo(name = "create_date") var createDate: Calendar = Calendar.getInstance()
    @ColumnInfo(name = "edited_date") var editedDate: Calendar = Calendar.getInstance()
}