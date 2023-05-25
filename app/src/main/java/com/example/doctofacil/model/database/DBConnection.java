package com.example.doctofacil.model.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection {
    private static final String DATABASE_NAME = "appointments.db";
    private static final int DATABASE_VERSION = 1;

    // Users table
    private static final String TABLE_USERS = "users";

    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_LAST_NAME = "last_name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_BIRTHDATE = "birthday";
    private static final String COLUMN_USER_CELLPHONE = "cellphone";
    private static final String COLUMN_USER_ROLE = "role";

    private static final String COLUMN_USER_ADDRESS = "address";
    private static final String COLUMN_USER_SPECIALTY = "specialty";
    private static final String COLUMN_USER_LICENSE_ID = "licence_id";

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
        Cursor cursor = db.query(DBHelper.TABLE_USERS, new String[]{DBHelper.COLUMN_USER_EMAIL},
                DBHelper.COLUMN_USER_EMAIL + "=? AND " + DBHelper.COLUMN_USER_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }
    @SuppressLint("Range")
    public String getUserRole(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {COLUMN_USER_ROLE};
        String selection = "email = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        String role = "";

        if (cursor != null && cursor.moveToFirst()) {
            role = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ROLE));
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return role;
    }

}
