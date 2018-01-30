package com.iip.datafusion.ums.model;

/**
 * Created by jingwei on 2017/12/20.
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String salt;

    public User(){
        id = 0;
        username = "";
        password = "";
        salt = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
