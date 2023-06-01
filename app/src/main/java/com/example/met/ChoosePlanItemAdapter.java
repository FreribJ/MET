package com.example.met;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.met.dataObjects.Activity;
import com.example.met.dataObjects.Plan;
import com.example.met.met.MetCalculator;

public class ChoosePlanItemAdapter extends ArrayAdapter<Plan> {
    private final Context context;

    DatabaseHelper db;
    Plan[] plans;

    MetCalculator metCalculator = new MetCalculator();

    public ChoosePlanItemAdapter(@NonNull Context context, int resource, @NonNull Plan[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.db = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return plans.length;
    }

    @Override
    public Plan getItem(int i) {
        return plans[i];
    }

    @Override
    public long getItemId(int i) {
        return plans[i].getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.fragment_choose_plan_item, viewGroup, false);

        TextView nameOfActivity = layout.findViewById(R.id.planName);
        nameOfActivity.setText(plans[i].getName());

        TextView nameOfSport = layout.findViewById(R.id.planActivityCount);
        nameOfSport.setText(nameOfSport.getText().toString().replace("${value}", String.valueOf(db.getPlanActivities(plans[i].getId()).length)));

        return layout;
    }

}
