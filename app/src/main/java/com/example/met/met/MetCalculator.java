package com.example.met.met;

import android.util.Log;

import com.example.met.dataObjects.Activity;

import java.util.ArrayList;
import java.util.Arrays;

public class MetCalculator {

    static Sport[] sports = new Sport[]{
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

    static Category[] categories = new Category[]{
            new Category("Aktivitätskategorie", Integer.MIN_VALUE, -1),
            new Category("Ungenügend", 0, 599),
            new Category("Niedrig", 600, 3999),
            new Category("Mittel", 4000, 7999),
            new Category("Hoch", 8000, Integer.MAX_VALUE),
    };

    public String[] getStringArray(HasName[] array) {
        if (array == null) return null;
        ArrayList<String> arrayList = new ArrayList<>();
        Arrays.stream(array).forEach(a -> arrayList.add(a.getName()));
        return arrayList.toArray(new String[0]);
    }

    public Category[] getCategoryArray() {
        return categories;
    }

    public Category getCategory(String name) {
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public String getCategoryName(int met) {
        for (Category category : categories) {
            if (category.getFrom() <= met && met <= category.getTo()) {
                return category.getName();
            }
        }
        return null;
    }

    public Sport[] getSportArray() {
        return sports;
    }

    public Sport getSport(String name) {
        for (Sport sport : sports) {
            if (sport.getName().equals(name)) {
                return sport;
            }
        }
        return null;
    }

    public Sport.Intensity[] getIntensityArray(Sport sport) {
        return sport.getIntensitys();
    }

    public Sport.Intensity[] getIntensityArray(String sportName) {
        Sport sport = getSport(sportName);
        return getIntensityArray(sport);
    }

    public Sport.Intensity getIntensity(Sport sport, String intensityName) {
        if (sport == null || intensityName.equals("")) return null;
        for (Sport.Intensity intensity : sport.getIntensitys()) {
            if (intensity.getName().equals(intensityName)) {
                return intensity;
            }
        }
        return null;
    }

    public Sport.Intensity getIntensity(String sportName, String intensityName) {
        if (sportName == null) return null;
        Sport sport = getSport(sportName);
        return getIntensity(sport, intensityName);
    }

    public double getMetMinutes(Activity activity) {
        Sport sport = getSport(activity.getSport());
        Sport.Intensity intensity = getIntensity(sport, activity.getIntensity());
        if (intensity == null) {
            return sport.getMet() * activity.getTime();
        } else {
            return intensity.getMet() * activity.getTime();
        }
    }

    public int getIndexOfArray(String[] array, String value) {
        if (array == null) {
            return 0;
        }
        int i = Arrays.asList(array).indexOf(value);
        return i == -1 ? 0 : i;
    }

    public int getIndexOfArray(HasName[] array, String value) {
        if (array == null) {
            return 0;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i].getName().equals(value)) {
                return i;
            }
        }
        return 0;
    }
}