package com.example.doctofacil.model;

import java.sql.Timestamp;

public class Appointment {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String state;
    private int recipeId;

    public Appointment(int appointmentId, int patientId, int doctorId, Timestamp startTime, Timestamp endTime,
                       String state, int recipeId) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.recipeId = recipeId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public String getState() {
        return state;
    }

    public int getRecipeId() {
        return recipeId;
    }
}
