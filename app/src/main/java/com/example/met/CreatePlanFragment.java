package com.example.met;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.met.dataObjects.Plan;
import com.example.met.dataObjects.Plan_Activity;
import com.example.met.databinding.FragmentCreatePlanBinding;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePlanFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String ARG_PARAM_PLAN_ID = "plan_id";

    int planId = -1;
    FragmentCreatePlanBinding binding;

    DatabaseHelper db;


    public CreatePlanFragment() {
        // Required empty public constructor
    }

    public static ActivityOverviewFragment newInstance(String param1, String param2) {
        ActivityOverviewFragment fragment = new ActivityOverviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            planId = getArguments().getInt(ARG_PARAM_PLAN_ID);
        }

        db = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreatePlanBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Plan plan = db.getPlan(planId);
        binding.planName.setText(plan.getName().equals("Neuer Plan") ? "" : plan.getName());

        Plan_Activity[] activities = db.getPlanActivities(planId);

        if(activities.length == 0 && plan.getName().equals("Neuer Plan")) {
            binding.overviewTextView.setText(getString(R.string.createNewPlan));
        }

        String[] activityNames = new String[activities.length];
        for (int i = 0; i < activities.length; i++) {
            activityNames[i] = activities[i].getName();
        }
        CreatePlanItemAdapter adapter = new CreatePlanItemAdapter(getContext(), R.layout.fragment_create_plan_item, activities, planId);
        binding.activityList.setAdapter(adapter);

        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String currentDate = String.format("%02d", currentDay) + "." + String.format("%02d",
                currentMonth) + "." + currentYear;
        binding.inputDate.setText(currentDate);

        binding.removePlanButton.setOnClickListener((v) -> {
            db.deletePlan(planId);
            Navigation.findNavController(view).popBackStack(R.id.choosePlanFragment, false);
        });

        binding.addActivityButton.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("plan_id", planId);
            bundle.putInt("plan_activity_id", -1);
            Navigation.findNavController(view).navigate(R.id.action_createPlanFragment_to_newActivityForPlanFragment, bundle);
        });
        binding.finishButton.setOnClickListener((v) -> {
            if (!binding.planName.getText().toString().equals("")) {
                db.updatePlan(planId, binding.planName.getText().toString());
                Navigation.findNavController(view).popBackStack(R.id.choosePlanFragment, false);
            } else {
                Toast.makeText(getContext(), "Bitte Namen eingeben", Toast.LENGTH_LONG).show();
            }
        });
        binding.usePlanButton.setOnClickListener((v) -> {
            if (binding.inputDate.getText().toString().matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                db.updatePlan(planId, binding.planName.getText().toString());
                applyPlanActivities();

                Navigation.findNavController(view).popBackStack(R.id.newDecisionFragment,
                        true);
            } else {
                Toast.makeText(getContext(), "Bitte Datum im Format DD.MM.YYYY eingeben",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void applyPlanActivities() {
        Plan_Activity[] plan_activities = db.getPlanActivities(planId);
        String date = binding.inputDate.getText().toString();
        for (Plan_Activity plan_activity : plan_activities) {
            db.insertActivity(plan_activity.getName(), plan_activity.getSport(),
                    plan_activity.getIntensity(), plan_activity.getTime(), date,
                    db.getUser().getWeight());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}