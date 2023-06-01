package com.example.met.dataObjects;

public class User {
    String name;
    int age;
    double weight;
    String category;

    public User(String name, int age, double weight, String category) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public String getCategory() {
        return category;
    }
}
