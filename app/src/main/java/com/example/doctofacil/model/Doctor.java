package com.example.doctofacil.model;

import java.util.Date;

public class Doctor extends User{
    private String doctorID; //cedula
    private String specialty; // especialidad
    private String address;

    public Doctor(String name, String lastName, String email, Date birthDate, String password,
                  String doctorID, String specialty, String address) {
        super(name, lastName, email, birthDate, password);
        this.doctorID = doctorID;
        this.specialty = specialty;
        this.address = address;
    }

}
