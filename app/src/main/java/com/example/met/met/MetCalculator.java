package com.example.met.met;

import java.util.ArrayList;
import java.util.Arrays;

public class MetCalculator {

    public static Sport[] activities = new Sport[]{
            new Sport("Joggen", new Sport.Intensity[]{
                    new Sport.Intensity("langsam", 9),
                    new Sport.Intensity("zügig", 12),
                    new Sport.Intensity("schnell", 15)}),
            new Sport("Radfahren", new Sport.Intensity[]{
                    new Sport.Intensity("< 15 km/h", 4),
                    new Sport.Intensity("< 19 km/h ", 6),
                    new Sport.Intensity("< 23 km/h ", 8),
                    new Sport.Intensity("< 26 km/h", 10),
                    new Sport.Intensity("< 30 km/h ", 12)}),
            new Sport("Schwimmen", new Sport.Intensity[]{
                    new Sport.Intensity("Rücken", 8),
                    new Sport.Intensity("Kraulen", 9),
                    new Sport.Intensity("Brust", 10),
                    new Sport.Intensity("Schmetterling", 13)}),
            new Sport("Klettern", 8),
            new Sport("Fußball", 7),
            new Sport("Basketball", 6),
            new Sport("Tennis", 7),
            new Sport("Badminton", 6),
            new Sport("Kraftsport", 5),
            new Sport("Volleyball", 4),
            new Sport("Tischtennis", 4)};

    public static Category[] categories = new Category[] {
            new Category("ungenügend", 0, 599),
            new Category("niedrig", 600, 3999),
            new Category("mittel", 4000, 7999),
            new Category("hoch", 8000, Integer.MAX_VALUE),
    };

    public String[] getCategoryArray() {
        ArrayList<String> arrayList = new ArrayList<>();
        Arrays.stream(categories).forEach(category -> arrayList.add(category.getName()));
        return arrayList.toArray(new String[0]);
    }

    public String[] getSportArray() {
        ArrayList<String> arrayList = new ArrayList<>();
        Arrays.stream(activities).forEach(sport -> arrayList.add(sport.getName()));
        return arrayList.toArray(new String[0]);
    }

    public String[] getIntensityArray(String intensityName) {
        ArrayList<String> arrayList = new ArrayList<>();
        Sport.Intensity[] intensities = null;
        for (Sport a:  activities) {
            if(a.getName() == intensityName)
                intensities = a.getIntensitys();
        }
        if (intensities == null) {
            return null;
        }
        Arrays.stream(intensities).forEach(a -> arrayList.add(a.getName()));
        return arrayList.toArray(new String[0]);
    }

    public int calculateMet(String activityName, int time) {
        return Arrays.stream(activities).findAny().get().getMet() * time;
    }

    public int calculateMet(String activityName, String subActivityname, int time) {

        Sport.Intensity[] sA = Arrays.stream(activities).findAny().get().getIntensitys();
        return Arrays.stream(sA).findAny().get().getMet() * time;
    }

    public int getIndexOfArray(String[] array, String value) {
        if (array == null) {
            return 0;
        }
        int i = Arrays.asList(array).indexOf(value);
        return i == -1 ? 0 : i;
    }

}