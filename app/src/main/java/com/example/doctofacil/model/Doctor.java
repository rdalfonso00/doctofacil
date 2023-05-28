package com.example.doctofacil.model;

import java.util.Date;

public class Doctor extends User{
    private String licence; //cedula
    private String specialty; // especialidad
    private String address;

    public Doctor(int user_id, String name, String lastName, String birthDate, String phone, String email,  String password,
                  String licence, String specialty, String address) {
        super(user_id, name, lastName, birthDate, phone, email, password, "doctor");
        this.licence = licence;
        this.specialty = specialty;
        this.address = address;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
