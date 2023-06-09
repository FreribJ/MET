package com.example.met.dataObjects;

public class User {
    String name;
    String dateOfBirth;
    double weight;
    String category;

    public User(String name, String dateOfBirth, double weight, String category) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public double getWeight() {
        return weight;
    }

    public String getCategory() {
        return category;
    }
}
