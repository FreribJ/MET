package com.example.met.dataObjects;

public class Plan_Activity {
    int id;
    String name;
    String sport;
    String intensity;
    double time;

    public Plan_Activity(int id, String name, String sport, String intensity, double time) {
        this.id = id;
        this.name = name;
        this.sport = sport;
        this.intensity = intensity;
        this.time = time;
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

}
