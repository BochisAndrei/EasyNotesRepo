package com.packg.easynotes.Elements;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity (tableName = "element_table")
public class Element {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    protected int id;
    @ColumnInfo(name = "element_type")
    protected String type;
    @ColumnInfo(name = "parent_id")
    protected int parentId;

    public String getType(){
        return this.type;
    }
    public int getId(){
        return this.id;
    }
}
