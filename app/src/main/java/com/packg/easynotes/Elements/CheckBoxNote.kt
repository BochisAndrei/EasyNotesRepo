package com.packg.easynotes.Elements

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "check_box_table",
    foreignKeys = [
        ForeignKey(
            entity = CrossNote::class,
            childColumns = ["parent_id"],
            parentColumns = ["cross_note_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class CheckBoxNote (@ColumnInfo(name = "name") val name : String,
                    @ColumnInfo(name = "checked") var checked : Boolean){

    @ColumnInfo(name = "checkbox_id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    @ColumnInfo(name = "parent_id", index = true)
    var parentId : Long = 0

}