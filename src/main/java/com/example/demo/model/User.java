package com.example.demo.model;

public class User {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;


    private boolean active;


    public User(String firstName, String lastName , boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = firstName + "." + lastName;
    }

    public User() {
        
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
