package com.packg.easynotes.Elements;

import java.util.ArrayList;

public class TextNote extends Element{
    protected String name;
    protected String text;

    public TextNote(int id, String name,String text){
        this.id = id;
        this.name = name;
        this.text = text;
        this.type = "TextNote";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }


}
