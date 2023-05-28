package com.example.doctofacil.model;

public class Recipe {
    private int recipeId;
    private int appointmentId;
    private int doctorId;
    private String diagnosis;
    private String prescription;
    private String duration;
    private String dosage;

    public Recipe(int recipeId, int appointmentId, int doctorId, String diagnosis, String prescription,
                  String duration, String dosage) {
        this.recipeId = recipeId;
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.duration = duration;
        this.dosage = dosage;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getDuration() {
        return duration;
    }

    public String getDosage() {
        return dosage;
    }
}
