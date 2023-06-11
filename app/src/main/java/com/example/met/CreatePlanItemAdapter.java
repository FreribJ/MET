package com.example.met;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.met.dataObjects.Plan;
import com.example.met.dataObjects.Plan_Activity;
import com.example.met.met.MetCalculator;

public class CreatePlanItemAdapter extends ArrayAdapter<Plan_Activity> {
    private final Context context;

    DatabaseHelper db;
    Plan_Activity[] plan_activities;

    MetCalculator metCalculator = new MetCalculator();

    int planId = -1;

    public CreatePlanItemAdapter(@NonNull Context context, int resource, @NonNull Plan_Activity[] objects, int planId) {
        super(context, resource, objects);
        this.context = context;
        this.plan_activities = objects;
        this.db = new DatabaseHelper(context);
        this.planId = planId;
    }

    @Override
    public int getCount() {
        return plan_activities.length;
    }

    @Override
    public Plan_Activity getItem(int i) {
        return plan_activities[i];
    }

    @Override
    public long getItemId(int i) {
        return plan_activities[i].getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.fragment_create_plan_item, viewGroup, false);

        TextView nameOfActivity = layout.findViewById(R.id.planActivityName);
        nameOfActivity.setText(plan_activities[i].getName());

        TextView nameOfSport = layout.findViewById(R.id.sportName);
        nameOfSport.setText(plan_activities[i].getSport());


        layout.setOnClickListener(view1 -> {

            Bundle bundle = new Bundle();
            bundle.putInt("plan_id", planId);
            bundle.putInt("plan_activity_id", plan_activities[i].getId());
            Navigation.findNavController(layout).navigate(R.id.action_createPlanFragment_to_newActivityForPlanFragment, bundle);
        });

        return layout;
    }

}
