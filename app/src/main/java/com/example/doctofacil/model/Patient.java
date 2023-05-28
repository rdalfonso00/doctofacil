package com.example.doctofacil.model;

import java.io.Serializable;
import java.util.Date;

public class Patient extends User implements Serializable{
    public Patient(int user_id, String name, String lastName, String birthDate, String phone, String email, String password) {
        super(user_id, name, lastName, birthDate, phone, email, password, "patient");
    }
}
