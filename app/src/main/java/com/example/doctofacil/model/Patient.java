package com.example.doctofacil.model;

import java.util.Date;

public class Patient extends User{
    public Patient(String name, String lastName, String email, Date birthDate, String password) {
        super(name, lastName, email, birthDate, password);
    }
}
