package com.example.met.met;

public class Activity {

    static class SubActivity {
        String name;
        int value;

        public SubActivity(String name, int value) {
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
    private SubActivity[] mets;

    public Activity(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Activity(String name, SubActivity[] subMets) {
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

    public SubActivity[] getSubActivitys() {
        return mets;
    }
}
