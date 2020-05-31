package com.packg.easynotes.Elements;

public class ProxyImage extends Element {
    protected String name;
    protected Image realImage;
    public ProxyImage(int id, String name){
        this.id = id;
        this.name = name;
        this.type = "ProxyImage";
    }

}
