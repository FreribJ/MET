package com.example.met.met;

import java.util.ArrayList;
import java.util.Arrays;

public class MetCalculator {

    public static Activity[] activities = new Activity[]{
            new Activity("Joggen", new Activity.SubActivity[]{
                    new Activity.SubActivity("langsam", 9),
                    new Activity.SubActivity("zügig", 12),
                    new Activity.SubActivity("schnell", 15)}),
            new Activity("Radfahren", new Activity.SubActivity[]{
                    new Activity.SubActivity("< 15 km/h", 4),
                    new Activity.SubActivity("< 19 km/h ", 6),
                    new Activity.SubActivity("< 23 km/h ", 8),
                    new Activity.SubActivity("< 26 km/h", 10),
                    new Activity.SubActivity("< 30 km/h ", 12)}),
            new Activity("Schwimmen", new Activity.SubActivity[]{
                    new Activity.SubActivity("Rücken", 8),
                    new Activity.SubActivity("Kraulen", 9),
                    new Activity.SubActivity("Brust", 10),
                    new Activity.SubActivity("Schmetterling", 13)}),
            new Activity("Klettern", 8),
            new Activity("Fußball", 7),
            new Activity("Basketball", 6),
            new Activity("Tennis", 7),
            new Activity("Badminton", 6),
            new Activity("Kraftsport", 5),
            new Activity("Volleyball", 4),
            new Activity("Tischtennis", 4)};

    public static Category[] categories = new Category[] {
            new Category("ungenügend", 0, 599),
            new Category("niedrig", 600, 3999),
            new Category("mittel", 4000, 7999),
            new Category("hoch", 8000, Integer.MAX_VALUE),
    };

    String[] getCategoryArray() {
        ArrayList<String> arrayList = new ArrayList<>();
        Arrays.stream(categories).forEach(category -> arrayList.add(category.getName()));
        return (String[]) arrayList.toArray();
    }

    String[] getActivityArray() {
        ArrayList<String> arrayList = new ArrayList<>();
        Arrays.stream(activities).forEach(activity -> arrayList.add(activity.getName()));
        return (String[]) arrayList.toArray();
    }

    String[] getSubActivityArray(String subActivityname) {
        ArrayList<String> arrayList = new ArrayList<>();
        Activity.SubActivity[] activity;
        for (var a:  activities) {
            if(a.getName() == subActivityname)
                activity = a.getSubActivitys();
        }
        Arrays.stream(activities).forEach(a -> arrayList.add(a.getName()));
        return (String[]) arrayList.toArray();
    }

    int calculateMet(String activityName, int time) {
        return Arrays.stream(activities).findAny().get().getMet() * time;
    }

    int calculateMet(String activityName, String subActivityname, int time) {

        var sA = Arrays.stream(activities).findAny().get().getSubActivitys();
        return Arrays.stream(sA).findAny().get().getMet() * time;
    }

}