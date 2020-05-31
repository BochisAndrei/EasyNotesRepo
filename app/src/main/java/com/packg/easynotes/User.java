package com.packg.easynotes;


public class User {
    private String userName;
    private String email;
    private String image;
    private String font;

    public User(String userName, String email, String image) {
        this.userName = userName;
        this.email = email;
        this.image = image;
        this.font = "normal";
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getUserName() {
        return userName;
    }

    public void setUser(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }



}
