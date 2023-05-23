package com.example.doctofacil.model;

import java.util.Date;

public abstract class User {
    private String name;
    private String lastName;
    private String email;
    private Date   birthDate;
    private String password;

    public User(String name, String lastName, String email, Date birthDate, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
