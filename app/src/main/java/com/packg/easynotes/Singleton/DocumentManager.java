package com.packg.easynotes.Singleton;

import com.packg.easynotes.User;

public class DocumentManager {
    private static DocumentManager instance=null;
    private User user = new User("", "","");
    private int currentFolderId;

    private DocumentManager(){
    }
    public int getCurrentFolderId() {
        return currentFolderId;
    }

    public void setCurrentFolderId(int currentFolderId) {
        this.currentFolderId = currentFolderId;
    }

    public static DocumentManager getInstance(){
        if(instance==null){
            instance=new DocumentManager();
        }
        return instance;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }


}