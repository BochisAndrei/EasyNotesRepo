package com.packg.easynotes.Elements

import androidx.room.ColumnInfo
import androidx.room.Embedded
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

    @ColumnInfo(name = "check_box1_name")
    var checkBox1Name: String = "...."
    @ColumnInfo(name = "check_box1_checked")
    var checkBox1Checked: Boolean = false

    @ColumnInfo(name = "check_box2_name")
    var checkBox2Name: String = "...."
    @ColumnInfo(name = "check_box2_checked")
    var checkBox2Checked: Boolean = false

    @ColumnInfo(name = "check_box3_name")
    var checkBox3Name: String = "...."
    @ColumnInfo(name = "check_box3_checked")
    var checkBox3Checked: Boolean = false

    @ColumnInfo(name = "check_box4_name")
    var checkBox4Name: String = "...."
    @ColumnInfo(name = "check_box4_checked")
    var checkBox4Checked: Boolean = false
}