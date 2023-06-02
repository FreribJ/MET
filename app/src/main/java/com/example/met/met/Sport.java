package com.example.met.met;

public class Sport implements HasName{

    static class Intensity implements HasName {
        String name;
        int value;

        public Intensity(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public int getMet() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    private String name;
    private int value;
    private Intensity[] mets;

    public Sport(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Sport(String name, Intensity[] subMets) {
        this.name = name;
        this.mets = subMets;
    }

    public boolean hasSubActivitys() {
        if (this.value == 0) {
            return true;
        }
        return false;
    }

    public int getMet() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Intensity[] getIntensitys() {
        return mets;
    }
}
