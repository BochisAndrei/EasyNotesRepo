package com.packg.easynotes.Elements;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "audio_table",
        foreignKeys = @ForeignKey(entity = Element.class,
                parentColumns = "id",
                childColumns = "id",
                onDelete = ForeignKey.CASCADE))
public class Audio extends Element {

    protected String name;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Audio(int id, String name){
        this.name = name;
    }

}
