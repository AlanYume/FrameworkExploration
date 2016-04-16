package org.smart4j.chapter2.model;

public class User {
    private String username;
    private String password;

    public User() {
    }

    public User(final String name, final String pwd) {
        this.username = name;
        this.password = pwd;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
