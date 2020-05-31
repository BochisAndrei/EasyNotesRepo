package com.packg.easynotes.Elements;

import java.util.ArrayList;
import java.util.Date;

public class Folder extends Element {
    protected String name;
    // TODO: 2020-03-31 set somehow an image
    protected int image;
    protected Date creationDate;
    protected Date lastEdit;
    protected ArrayList<Element> elementsList = new ArrayList<>();


    public Folder(int id, String name, int image){
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = "Folder";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Element> getElementsList() {
        return elementsList;
    }

    public void setElementsList(ArrayList<Element> elementsList) {
        this.elementsList = elementsList;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(Date lastEdit) {
        this.lastEdit = lastEdit;
    }


    public void add(Element element){
        elementsList.add(element);
    }

}
