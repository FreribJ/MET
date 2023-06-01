package com.example.met.dataObjects;

import java.util.ArrayList;

public class Plan {

    int id;
    String name;
    ArrayList<Plan_Activity> activities;

    public Plan(int id, String name) {
        this.id = id;
        this.name = name;
        activities = new ArrayList<Plan_Activity>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addActivity(Plan_Activity activity) {
        activities.add(activity);
    }

    public Plan_Activity[] getActivities() {
        Plan_Activity[] activitiesArray = new Plan_Activity[activities.size()];
        for (int i = 0; i < activities.size(); i++) {
            activitiesArray[i] = activities.get(i);
        }
        return activitiesArray;
    }

    public void removeActivity(Plan_Activity activity) {
        activities.remove(activity);
    }

    public Plan_Activity findActivity(String name) {
        for (Plan_Activity activity : activities) {
            if (activity.getName().equals(name)) {
                return activity;
            }
        }
        return null;
    }
}
