package com.example.met.dataObjects;

public class Activity {
    int id;
    String name;
    String sport;
    String intensity;
    double time;
    String date;
    double weightOfUser;

    public Activity(int id, String name, String sport, String intensity, double time, String date, double weightOfUser) {
        this.id = id;
        this.name = name;
        this.sport = sport;
        this.intensity = intensity;
        this.time = time;
        this.date = date;
        this.weightOfUser = weightOfUser;
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

    public double getWeightOfUser() {
        return weightOfUser;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id + '\'' +
                ", name='" + name + '\'' +
                ", sport='" + sport + '\'' +
                ", intensity='" + intensity + '\'' +
                ", time=" + time + '\'' +
                ", date='" + date + '\'' +
                ", weightOfUser=" + weightOfUser +
                '}';
    }
}
