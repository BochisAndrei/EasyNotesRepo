package com.packg.easynotes.Elements;

public class Image extends Element {
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image(int id, String name){
        this.id = id;
        this.name = name;
        this.type = "Image";
    }

}
