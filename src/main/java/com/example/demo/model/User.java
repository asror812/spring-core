package com.example.demo.model;

public class User {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean active;

    public User() {
        
    }

    public User(String firstName, String lastName, String username, String password ,boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.active = active;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
