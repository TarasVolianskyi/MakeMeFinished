package com.myApp.tarasvolianskyi.makemefinished;

public class TasksPojo {

    private String uidAuth;
    private String userIdGenereted;
    private String userName;
    private String userCategory;

    public TasksPojo() {
    }

    public TasksPojo(String uidAuth, String userIdGenereted, String userName, String userCategory) {
        this.uidAuth = uidAuth;
        this.userIdGenereted = userIdGenereted;
        this.userName = userName;
        this.userCategory = userCategory;
    }

    public String getUidAuth() {
        return uidAuth;
    }

    public void setUidAuth(String uidAuth) {
        this.uidAuth = uidAuth;
    }

    public String getUserIdGenereted() {
        return userIdGenereted;
    }

    public void setUserIdGenereted(String userIdGenereted) {
        this.userIdGenereted = userIdGenereted;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }
}