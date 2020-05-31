package com.packg.easynotes.Singleton;

import android.content.Context;

import com.packg.easynotes.Elements.Element;
import com.packg.easynotes.Elements.Event;
import com.packg.easynotes.SharedPArray.PrefConfigNotesArray;
import com.packg.easynotes.User;

import java.util.ArrayList;

public class DocumentManager {
    private static DocumentManager instance=null;
    private User user = new User("", "","");
    private ArrayList<Element> list = new ArrayList<Element>();
    private ArrayList<Event> events = new ArrayList<Event>();
    private int currentFolderId;

    private DocumentManager(){
    }
    public ArrayList<Event> getEvents() {
        return events;
    }

    public int getCurrentFolderId() {
        return currentFolderId;
    }

    public void setCurrentFolderId(int currentFolderId) {
        this.currentFolderId = currentFolderId;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
    public void addEvent(Event event, Context context){
        this.events.add(event);
        PrefConfigNotesArray.writeEventListInPref(context, this.events);
    }
    public static DocumentManager getInstance(){
        if(instance==null){
            instance=new DocumentManager();
        }
        return instance;
    }

    public ArrayList<Element> getList() {
        return list;
    }

    public void setList(ArrayList<Element> list) {
        this.list = list;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }


}