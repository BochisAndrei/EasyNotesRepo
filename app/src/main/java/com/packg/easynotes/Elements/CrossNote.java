package com.packg.easynotes.Elements;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.ArrayList;
@Entity(tableName = "cross_note_table",
        foreignKeys = @ForeignKey(entity = Element.class,
                parentColumns = "id",
                childColumns = "id",
                onDelete = ForeignKey.CASCADE))
public class CrossNote extends Element {
    @ColumnInfo(name = "name")
    protected String name;

    protected ArrayList<CheckBox> list;

    public String getName() {
        return name;
    }

    public CrossNote(int id, String name){
        this.id = id;
        this.name = name;
        this.type = "CrossNote";
    }

}
