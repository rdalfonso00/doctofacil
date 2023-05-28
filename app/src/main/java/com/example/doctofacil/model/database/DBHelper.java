package com.example.doctofacil.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Timestamp;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "appointments.db";
    private static final int DATABASE_VERSION = 2;

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

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the doctor table
        String createUserTable = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_LAST_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT UNIQUE,"
                + COLUMN_USER_PASSWORD + " TEXT,"
                + COLUMN_USER_BIRTHDATE + " TEXT,"
                + COLUMN_USER_CELLPHONE + " TEXT,"
                + COLUMN_USER_ROLE + " TEXT,"
                + COLUMN_USER_ADDRESS + " TEXT,"
                + COLUMN_USER_SPECIALTY + " TEXT,"
                + COLUMN_USER_LICENSE_ID + " TEXT"
                + ")";
        db.execSQL(createUserTable);

        // Create appointments table
        String createAppointmentTableQuery = "CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                COLUMN_APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PATIENT_ID + " INTEGER, " +
                COLUMN_DOCTOR_ID + " INTEGER, " +
                COLUMN_START_TIME + " TIMESTAMP, " +
                COLUMN_END_TIME + " TIMESTAMP, " +
                COLUMN_STATE + " TEXT, " +
                COLUMN_RECIPE_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_PATIENT_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_DOCTOR_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_RECIPE_ID + ") REFERENCES " + TABLE_RECIPES + "(" + COLUMN_RECIPE_ID + "))";
        db.execSQL(createAppointmentTableQuery);

        // Create recipes table
        String createRecipeTableQuery = "CREATE TABLE " + TABLE_RECIPES + " (" +
                COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DOCTOR_USER_ID + " INTEGER, " +
                COLUMN_DIAGNOSIS + " TEXT, " +
                COLUMN_PRESCRIPTION + " TEXT, " +
                COLUMN_DURATION + " INTEGER, " +
                COLUMN_DOSAGE + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_DOCTOR_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";
        db.execSQL(createRecipeTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
