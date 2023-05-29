package com.example.doctofacil.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Appointment implements Serializable {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String state;
    private int recipeId;
    private String comments;
    private boolean isOnline;

    public Appointment(int appointmentId, int patientId, int doctorId, Timestamp startTime, Timestamp endTime,
                       String state, int recipeId, String comments, boolean isOnline) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.recipeId = recipeId;
        this.comments = comments;
        this.isOnline = isOnline;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
