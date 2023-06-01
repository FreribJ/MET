package com.example.met.dataObjects;

public class Activity {
    int id;
    String name;
    String sport;
    String intensity;
    double time;
    String date;

    public Activity(int id, String name, String sport, String intensity, double time, String date) {
        this.id = id;
        this.name = name;
        this.sport = sport;
        this.intensity = intensity;
        this.time = time;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSport() {
        return sport;
    }

    public String getIntensity() {
        return intensity;
    }

    public double getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}
