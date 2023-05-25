package com.example.met;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {

    public enum Datbases {
        user,
        activities
    }
    private static String DATABASE_NAME = "user";
    private static int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE user (id INTEGER PRIMARY KEY, name VARCHAR(255), age INTEGER, weight DOUBLE, category VARCHAR(255));"); //ENUM('ungenügend', 'niedrig', 'mittel', 'hoch')
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    boolean checkForUser() {
        return true;
    }

    void insertUser() {

    }

    void changeUser() {

    }
}