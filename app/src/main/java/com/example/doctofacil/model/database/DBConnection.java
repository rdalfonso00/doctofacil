package com.example.doctofacil.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.doctofacil.model.Appointment;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.model.Recipe;
import com.example.doctofacil.model.User;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBConnection {
    private static final String DATABASE_NAME = "appointments.db";
    private static final int DATABASE_VERSION = 1;

    // Users table
    public static final String TABLE_USERS = "users";

    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_LAST_NAME = "last_name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_BIRTHDATE = "birthday";
    public static final String COLUMN_USER_CELLPHONE = "cellphone";
    public static final String COLUMN_USER_ROLE = "role";

    public static final String COLUMN_USER_ADDRESS = "address";
    public static final String COLUMN_USER_SPECIALTY = "specialty";
    public static final String COLUMN_USER_LICENSE_ID = "licence_id";

    // appointments table
    private static final String TABLE_APPOINTMENTS = "appointments";
    private static final String COLUMN_APPOINTMENT_ID = "appointment_id";
    private static final String COLUMN_PATIENT_ID = "patient_id";
    private static final String COLUMN_DOCTOR_ID = "doctor_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_START_TIME = "start_time";
    private static final String COLUMN_END_TIME = "end_time";
    private static final String COLUMN_STATE = "state";
    private static final String COLUMN_RECIPE_ID = "recipe_id";

    // recipes table
    private static final String TABLE_RECIPES = "recipes";
    //private static final String COLUMN_RECIPE_ID = "recipe_id";
    private static final String COLUMN_DOCTOR_USER_ID = "doctor_user_id";
    private static final String COLUMN_DIAGNOSIS = "diagnosis";
    private static final String COLUMN_PRESCRIPTION = "prescription";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_DOSAGE = "dosage";

    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    public DBConnection(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long registerUser(String firstName, String lastName, String email, String password, String role,
                             String address, String birthdate, String cellphone, String speciality, String licenseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, firstName);
        values.put(COLUMN_USER_LAST_NAME, lastName);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        values.put(COLUMN_USER_ROLE, role);
        values.put(COLUMN_USER_BIRTHDATE, birthdate);
        values.put(COLUMN_USER_CELLPHONE, cellphone);
        values.put(COLUMN_USER_ADDRESS, address);
        values.put(COLUMN_USER_SPECIALTY, speciality);
        values.put(COLUMN_USER_LICENSE_ID, licenseId);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }
    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_USERS,
                new String[]{DBHelper.COLUMN_USER_EMAIL},
                DBHelper.COLUMN_USER_EMAIL + "=? AND " + DBHelper.COLUMN_USER_PASSWORD + "=?",
                new String[]{email, password},
                null,
                null,
                null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                COLUMN_USER_ID,
                COLUMN_USER_ROLE,
        };

        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        User user = null;

        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
            String role = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ROLE));

            user = new User(userId, null, null, null, null, null,null, role);
        }

        if (cursor != null) {
            cursor.close();
        }

        return user;
    }

    public Patient getPatientById(int patientId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_LAST_NAME,
                COLUMN_USER_CELLPHONE,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_BIRTHDATE,
        };

        String selection = COLUMN_USER_ID + " = ? AND " + COLUMN_USER_ROLE + " = ?";
        String[] selectionArgs = { String.valueOf(patientId), "patient" };

        Cursor cursor = db.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        Log.i("poncho", "pat id before query db"+ patientId);

        Patient patient = null;
        if (cursor == null)
            Log.i("poncho", "CURSOR NULL AAAA");
        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_LAST_NAME));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_CELLPHONE));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD));
            String birthdate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_BIRTHDATE));

            patient = new Patient(userId, firstName, lastName, birthdate, phone, email, password);
            Log.i("poncho", "pat fname db " + patient.getName());
        }

        if (cursor != null) {
            cursor.close();
        }

        return patient;
    }


    public List<User> getAllDoctors() {
        List<User> doctors = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_LAST_NAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_BIRTHDATE,
                COLUMN_USER_CELLPHONE,
                COLUMN_USER_ROLE,
                COLUMN_USER_ADDRESS,
                COLUMN_USER_SPECIALTY,
                COLUMN_USER_LICENSE_ID
        };

        String selection = COLUMN_USER_ROLE + " = ?";
        String[] selectionArgs = { "doctor" };

        Cursor cursor = db.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_LAST_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD));
                String birthdate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_BIRTHDATE));
                String cellphone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_CELLPHONE));
                String role = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ROLE));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ADDRESS));
                String speciality = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_SPECIALTY));
                String licence = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_LICENSE_ID));
                //TODO: fix and uncomment next line
                /*
                //Doctor doctor = new Doctor(userId, firstName, lastName, email, password, birthdate, cellphone, role, address, speciality, licence);
                //doctors.add(doctor);

                 */
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return doctors;
    }


    public long addAppointment(int patientId, int doctorId, Timestamp startTime, Timestamp endTime,
                               String state, int recipeId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENT_ID, patientId);
        values.put(COLUMN_DOCTOR_ID, doctorId);
        values.put(COLUMN_START_TIME, startTime.toString());
        values.put(COLUMN_END_TIME, endTime.toString());
        values.put(COLUMN_STATE, state);
        values.put(COLUMN_RECIPE_ID, recipeId);
        return db.insert(TABLE_APPOINTMENTS, null, values);
    }

    // always run before addAppointment()
    public boolean isDoctorAvailable(int doctorId, Timestamp startTime, Timestamp endTime) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Prepare the selection query
        String selection = COLUMN_DOCTOR_ID + " = ? AND ((" +
                COLUMN_START_TIME + " <= ? AND " + COLUMN_END_TIME + " > ?) OR (" +
                COLUMN_START_TIME + " < ? AND " + COLUMN_END_TIME + " >= ?) OR (" +
                COLUMN_START_TIME + " >= ? AND " + COLUMN_END_TIME + " <= ?))";

        // Prepare the selection arguments
        String[] selectionArgs = {
                String.valueOf(doctorId),
                String.valueOf(startTime.getTime()), String.valueOf(startTime.getTime()),
                String.valueOf(endTime.getTime()), String.valueOf(endTime.getTime()),
                String.valueOf(startTime.getTime()), String.valueOf(endTime.getTime())
        };

        // Execute the query
        Cursor cursor = db.query(TABLE_APPOINTMENTS, null, selection, selectionArgs, null, null, null);

        // Check if any overlapping appointment exists
        boolean isAvailable = cursor.getCount() == 0;

        cursor.close();
        return isAvailable;
    }

    public boolean updateAppointmentState(int appointmentId, String newState) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STATE, newState);

        String selection = COLUMN_APPOINTMENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(appointmentId)};

        int rowsAffected = db.update(TABLE_APPOINTMENTS, values, selection, selectionArgs);

        return rowsAffected > 0;
    }

    public boolean deleteAppointment(int appointmentId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = COLUMN_APPOINTMENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(appointmentId)};

        int rowsDeleted = db.delete(TABLE_APPOINTMENTS, selection, selectionArgs);

        return rowsDeleted > 0;
    }

    public List<Appointment> getAppointmentsForPatient(int patientId, String state) {
        List<Appointment> appointments = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                COLUMN_APPOINTMENT_ID,
                COLUMN_PATIENT_ID,
                COLUMN_DOCTOR_ID,
                COLUMN_START_TIME,
                COLUMN_END_TIME,
                COLUMN_STATE,
                COLUMN_RECIPE_ID
        };

        String selection = COLUMN_PATIENT_ID + " = ? AND " + COLUMN_STATE + " = ?";
        String[] selectionArgs = {
                String.valueOf(patientId),
                state
        };

        Cursor cursor = db.query(
                TABLE_APPOINTMENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int appointmentId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_ID));
                int doctorId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_ID));
                Timestamp startTime = Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_TIME)));
                Timestamp endTime = Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_TIME)));
                String appointmentState = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATE));
                int recipeId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID));

                Appointment appointment = new Appointment(appointmentId, patientId, doctorId, startTime, endTime, appointmentState, recipeId);
                appointments.add(appointment);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return appointments;
    }

    public List<Appointment> getAppointmentsForDoctor(int doctorId, String state) {
        List<Appointment> appointments = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                COLUMN_APPOINTMENT_ID,
                COLUMN_PATIENT_ID,
                COLUMN_DOCTOR_ID,
                COLUMN_START_TIME,
                COLUMN_END_TIME,
                COLUMN_STATE,
                COLUMN_RECIPE_ID
        };

        String selection = COLUMN_DOCTOR_ID + " = ? AND " + COLUMN_STATE + " = ?";
        String[] selectionArgs = {
                String.valueOf(doctorId),
                state
        };

        Cursor cursor = db.query(
                TABLE_APPOINTMENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int appointmentId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_ID));
                int patientId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_ID));
                Timestamp startTime = Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_TIME)));
                Timestamp endTime = Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_TIME)));
                String appointmentState = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATE));
                int recipeId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID));

                Appointment appointment = new Appointment(appointmentId, patientId, doctorId, startTime, endTime, appointmentState, recipeId);
                appointments.add(appointment);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return appointments;
    }

    public Recipe getRecipeForAppointment(int appointmentId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                COLUMN_RECIPE_ID,
                COLUMN_DOCTOR_ID,
                COLUMN_DIAGNOSIS,
                COLUMN_PRESCRIPTION,
                COLUMN_DURATION,
                COLUMN_DOSAGE
        };

        String selection = COLUMN_APPOINTMENT_ID + " = ?";
        String[] selectionArgs = { String.valueOf(appointmentId) };

        Cursor cursor = db.query(
                TABLE_RECIPES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Recipe recipe = null;

        if (cursor != null && cursor.moveToFirst()) {
            int recipeId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID));
            int doctorId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_ID));
            String diagnosis = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIAGNOSIS));
            String prescription = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRESCRIPTION));
            String duration = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION));
            String dosage = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOSAGE));

            recipe = new Recipe(recipeId, appointmentId, doctorId, diagnosis, prescription, duration, dosage);
        }

        if (cursor != null) {
            cursor.close();
        }

        return recipe;
    }

}
