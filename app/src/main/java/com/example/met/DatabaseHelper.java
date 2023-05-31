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
    private static String DATABASE_NAME = "met_app.db";
    private static int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE user (id INTEGER PRIMARY KEY, name VARCHAR(255), age INTEGER, weight DOUBLE, category VARCHAR(255));");
        sqLiteDatabase.execSQL("CREATE TABLE activitys (id INTEGER PRIMARY KEY, name VARCHAR(255), sport VARCHAR(255), intensity VARCHAR(255), time INTEGER, date DATE, id_user BIGINT REFERENCES user(id));");
        sqLiteDatabase.execSQL("CREATE TABLE plans (id INTEGER PRIMARY KEY, name VARCHAR(255), age INTEGER, weight DOUBLE, category VARCHAR(255));");
        sqLiteDatabase.execSQL("CREATE TABLE plan_activitys (id INTEGER PRIMARY KEY, name VARCHAR(255), sport VARCHAR(255), intensity VARCHAR(255), time INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    boolean checkForUser() {
        return true;
    }

    void insertUser(String name, int age, double weight, String category) {
        getWritableDatabase().execSQL("INSERT INTO user (name, age, weight, category) VALUES ('" + name + "', " + age + ", " + weight + ", '" + category + "');");
    }

    void insertActivity(String name, String sport, String intensity, int time) {
        getWritableDatabase().execSQL("INSERT INTO activitys (name, sport, intensity, time) VALUES ('" + name + "', '" + sport + "', '" + intensity + "', " + time + ");");
    }

    void insertPlan(String name, int age, double weight, String category) {
        getWritableDatabase().execSQL("INSERT INTO plans (name, age, weight, category) VALUES ('" + name + "', " + age + ", " + weight + ", '" + category + "');");
    }

    void insertPlanActivity(String name, String sport, String intensity, int time) {
        getWritableDatabase().execSQL("INSERT INTO plan_activitys (name, sport, intensity, time) VALUES ('" + name + "', '" + sport + "', '" + intensity + "', " + time + ");");
    }
}