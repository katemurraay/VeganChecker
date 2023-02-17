package com.kmm.vegancheckerapp.model;

public class User {
    private int userID;
    private String userName, email, firstName, surName, password, userType;

    public User(int userID, String userName, String email, String firstName, String surName, String password, String userType) {
       this.userID =userID;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.surName = surName;
        this.password = password;
        this.userType = userType;
    }

    public User(){
        userID = 0;
        userName = "";
        email = "";
        firstName ="";
        surName="";
        password="";
        userType = "";

    }
    // <editor-fold default state="collapsed" desc="Getters and Setters">

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    // </editor-fold>
}


