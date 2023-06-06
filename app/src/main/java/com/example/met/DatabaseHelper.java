package com.example.met;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.met.dataObjects.Activity;
import com.example.met.dataObjects.Plan;
import com.example.met.dataObjects.Plan_Activity;
import com.example.met.dataObjects.User;

import java.util.ArrayList;

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
        sqLiteDatabase.execSQL("CREATE TABLE activitys (id INTEGER PRIMARY KEY, name VARCHAR(255), sport VARCHAR(255), intensity VARCHAR(255), time DOUBLE, date DATE, id_user BIGINT REFERENCES user(id), weightOfUser DOUBLE);");
        sqLiteDatabase.execSQL("CREATE TABLE plans (id INTEGER PRIMARY KEY, name VARCHAR(255), age INTEGER, weight DOUBLE, category VARCHAR(255));");
        sqLiteDatabase.execSQL("CREATE TABLE plan_activitys (id INTEGER PRIMARY KEY, name VARCHAR(255), sport VARCHAR(255), intensity VARCHAR(255), time DOUBLE, id_plan INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    boolean checkForUser() {
        return true;
    }

    void insertUser(String name, int age, double weight, String category) {
        getWritableDatabase().execSQL("INSERT INTO user (name, age, weight, category) VALUES ('" + name + "', " + age + ", " + weight + ", '" + category + "');");
        Log.d("insertUser", "name: " + name + ", age: " + age + ", weight: " + weight + ", category: " + category);
    }

    void updateUser(String name, int age, double weight, String category) {
        getWritableDatabase().execSQL("UPDATE user SET name = '" + name + "', age = " + age + ", weight = " + weight + ", category = '" + category + "' WHERE id = 1;");
        Log.d("updateUser", "name: " + name + ", age: " + age + ", weight: " + weight + ", category: " + category);
    }

    User getUser() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM user;", null);
        String name = "";
        int age = 0;
        double weight = 0;
        String category = "";
        if (cursor.moveToNext()) {
            name = cursor.getString(1);
            age = cursor.getInt(2);
            weight = cursor.getDouble(3);
            category = cursor.getString(4);
            Log.d("getUser", "name: " + name + ", age: " + age + ", weight: " + weight + ", category: " + category);
            cursor.close();
            return new User(name, age, weight, category);
        }
        cursor.close();
        return null;
    }


    void insertActivity(String name, String sport, String intensity, double time, String date, double weightOfUser) {
        getWritableDatabase().execSQL("INSERT INTO activitys (name, sport, intensity, time, date, weightOfUser) VALUES ('" + name + "', '" + sport + "', '" + intensity + "', " + time + ", '" + date + "', " + weightOfUser + ");");
    }

    void updateActivity(int id, String name, String sport, String intensity, double time, String date) {
        getWritableDatabase().execSQL("UPDATE activitys SET name = '" + name + "', sport = '" + sport + "', intensity = '" + intensity + "', time = " + time + ", date = '" + date + "' WHERE id = " + id + ";");
    }

    Activity[] getActivities() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM activitys;", null);
        ArrayList<Activity> activities = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String sport = cursor.getString(2);
            String intensity = cursor.getString(3);
            double time = cursor.getDouble(4);
            String date = cursor.getString(5);
            double weightOfUser = cursor.getDouble(7); //Skipping id_user column, which at the moment is empty
            Log.d("getActivities", "id: " + id + ", name: " + name + ", sport: " + sport + ", intensity: " + intensity + ", time: " + time + ", date: " + date);
            activities.add(new Activity(id, name, sport, intensity, time, date, weightOfUser));
        }
        cursor.close();
        return activities.toArray(new Activity[0]);
    }

    Activity getActivity(int id) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM activitys WHERE id = " + id + ";", null);

        if (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String sport = cursor.getString(2);
            String intensity = cursor.getString(3);
            double time = cursor.getDouble(4);
            String date = cursor.getString(5);
            double weightOfUser = cursor.getDouble(6);
            Log.d("getActivity", "id: " + id + ", name: " + name + ", sport: " + sport + ", intensity: " + intensity + ", time: " + time + ", date: " + date);
            cursor.close();
            return new Activity(id, name, sport, intensity, time, date, weightOfUser);
        }
        cursor.close();
        return null;
    }

    int getActivityId(int index) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM activitys", null);
        for (int i = 0; i < index; i++) {
            if (!cursor.moveToNext()) {
                cursor.close();
                return -1;
            }
        }
        if (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        cursor.close();
        return -1;
    }

    void deleteActivity(int id) {
        getWritableDatabase().execSQL("DELETE FROM activitys WHERE id = " + id + ";");
    }

    int insertPlan(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        long id = getWritableDatabase().insert("plans", null, contentValues);
        return (int) id;
    }

    void updatePlan(int id, String name) {
        getWritableDatabase().execSQL("UPDATE plans SET name = '" + name + "' WHERE id = " + id + ";");
    }

    void deletePlan(int id) {
        getWritableDatabase().execSQL("DELETE FROM plans WHERE id = " + id + ";");
    }

    Plan getPlan(int id) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM plans WHERE id = " + id + ";", null);

        if (cursor.moveToNext()) {
            String name = cursor.getString(1);
            Log.d("getPlan", "id: " + id + ", name: " + name);
            cursor.close();
            return new Plan(id, name);
        }
        cursor.close();
        return null;
    }

    int getPlanId(int index) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM plans", null);

        for (int i = 0; i < index; i++) {
            if (!cursor.moveToNext()) {
                cursor.close();
                return -1;
            }
        }
        if (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        cursor.close();
        return -1;
    }

    Plan[] getPlans() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM plans;", null);
        ArrayList<Plan> plans = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            Log.d("getPlans", "id: " + id + ", name: " + name);
            plans.add(new Plan(id, name));
        }
        cursor.close();

        return plans.toArray(new Plan[0]);
    }

    void insertPlanActivity(String name, String sport, String intensity, double time, int plan_id) {
        getWritableDatabase().execSQL("INSERT INTO plan_activitys (name, sport, intensity, time, id_plan) VALUES ('" + name + "', '" + sport + "', '" + intensity + "', " + time + ", " + plan_id + ");");
    }

    void updatePlanActivity(int id, String name, String sport, String intensity, double time) {
        getWritableDatabase().execSQL("UPDATE plan_activitys SET name = '" + name + "', sport = '" + sport + "', intensity = '" + intensity + "', time = " + time + " WHERE id = " + id + ";");
    }

    Plan_Activity getPlanActivity(int id) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM plan_activitys WHERE id = " + id + ";", null);

        if (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String sport = cursor.getString(2);
            String intensity = cursor.getString(3);
            int time = cursor.getInt(4);
            Log.d("getPlanActivity", "id: " + id + ", name: " + name + ", sport: " + sport + ", intensity: " + intensity + ", time: " + time);
            cursor.close();
            return new Plan_Activity(id, name, sport, intensity, time);
        }
        cursor.close();
        return null;
    }

    int getPlanActivityId(int plan_id, int index) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM plan_activitys WHERE id_plan = " + plan_id + ";", null);

        for (int i = 0; i < index; i++) {
            if (!cursor.moveToNext()) {
                cursor.close();
                return -1;
            }
        }
        if (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        cursor.close();
        return -1;
    }
    Plan_Activity[] getPlanActivities(int plan_id) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM plan_activitys WHERE id_plan = " + plan_id + ";", null);
        ArrayList<Plan_Activity> plan_activities = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String sport = cursor.getString(2);
            String intensity = cursor.getString(3);
            int time = cursor.getInt(4);
            Log.d("getPlanActivities", "id: " + id + ", name: " + name + ", sport: " + sport + ", intensity: " + intensity + ", time: " + time);
            plan_activities.add(new Plan_Activity(id, name, sport, intensity, time));
        }
        cursor.close();
        return plan_activities.toArray(new Plan_Activity[0]);
    }

    void deletePlanActivity(int id) {
        getWritableDatabase().execSQL("DELETE FROM plan_activitys WHERE id = " + id + ";");
    }
}