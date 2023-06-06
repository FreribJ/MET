package com.example.met;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.met.dataObjects.Activity;
import com.example.met.met.MetCalculator;

public class ActivityOverviewItemAdapter extends ArrayAdapter<Activity> {

    private final Context context;

    Activity[] activities;

    MetCalculator metCalculator = new MetCalculator();

    public ActivityOverviewItemAdapter(@NonNull Context context, int resource, @NonNull Activity[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.activities = objects;
    }


    @Override
    public int getCount() {
        return activities.length;
    }

    @Override
    public Activity getItem(int i) {
        return activities[i];
    }

    @Override
    public long getItemId(int i) {
        return activities[i].getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.fragment_activity_overview_item, viewGroup, false);

        TextView nameOfActivity = layout.findViewById(R.id.nameOfActivity);
        nameOfActivity.setText(activities[i].getName());

        TextView nameOfSport = layout.findViewById(R.id.nameOfSport);
        nameOfSport.setText(activities[i].getSport());

        TextView valueOfMET = layout.findViewById(R.id.valueOfMET);
        int metMinutes = (int) metCalculator.getMetMinutes(activities[i]);
        valueOfMET.setText(String.valueOf((int)(metMinutes * activities[i].getWeightOfUser()) / 60) + " kcal\n" +
                "(" + String.valueOf(metMinutes) + " MET-Minuten)");


        TextView valueOfTime = layout.findViewById(R.id.valueOfTime);
        valueOfTime.setText("Dauer: " + String.valueOf(activities[i].getTime()));


        return layout;
    }
}
