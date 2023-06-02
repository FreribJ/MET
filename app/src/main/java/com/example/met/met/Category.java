package com.example.met.met;

public class Category implements HasName {
    private String name;
    private int from;
    private int to;

    public Category(String name, int from, int to) {
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
