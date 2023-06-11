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

import com.example.met.dataObjects.Plan_Activity;
import com.example.met.databinding.FragmentNewActivityForPlanBinding;
import com.example.met.met.MetCalculator;


public class NewActivityForPlanFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String ARG_PARAM_PLAN_ACTIVITY_ID = "plan_activity_id";
    private static final String ARG_PARAM_PLAN_ID = "plan_id";

    int planActivityId = -1;
    int planId = -1;

    FragmentNewActivityForPlanBinding binding;

    MetCalculator metCalculator = new MetCalculator();

    DatabaseHelper db;

    public NewActivityForPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            planActivityId = getArguments().getInt(ARG_PARAM_PLAN_ACTIVITY_ID);
            planId = getArguments().getInt(ARG_PARAM_PLAN_ID);
        }

        db = new DatabaseHelper(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewActivityForPlanBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (planActivityId == -1) {
            binding.overviewTextView.setText(getString(R.string.createNewActivity));
            binding.activityCreationFinished.setText(getString(R.string.create));
            binding.removeActivityButton.setText(getString(R.string.cancel));
        }

        String[] sports = metCalculator.getStringArray(metCalculator.getSportArray());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, sports);
        binding.inputSport.setAdapter(adapter);
        binding.inputSport.setOnItemSelectedListener(this);

        binding.removeActivityButton.setOnClickListener(view1 -> {
            db.deletePlanActivity(planActivityId);
            Navigation.findNavController(view).popBackStack();
        });

        binding.activityCreationFinished.setOnClickListener(view1 -> {
            String name = binding.inputName.getText().toString();
            String sport = binding.inputSport.getSelectedItem().toString();
            String intensity = binding.inputIntensity.getSelectedItem().toString();
            double duration = !binding.inputTime.getText().toString().equals("") ?
                    Double.parseDouble(binding.inputTime.getText().toString()) : 0;
            if (!name.equals("") && !sport.equals("") && duration != 0) {

                Log.d("NewActivityFragment",
                        "onViewCreated: " + name + " " + sport + " " + intensity + " " + duration);

                if (planActivityId != -1)
                    db.updatePlanActivity(planActivityId, name, sport, intensity, duration);
                else db.insertPlanActivity(name, sport, intensity, duration, planId);

                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(getContext(), "Bitte fülle alle Felder aus", Toast.LENGTH_SHORT).show();
            }
        });

        if (planActivityId != -1) {
            Plan_Activity activity = db.getPlanActivity(planActivityId);
            binding.inputName.setText(activity.getName());
            binding.inputSport.setSelection(metCalculator.getIndexOfArray(metCalculator.getSportArray(), activity.getSport()));
            binding.inputIntensity.setSelection(metCalculator.getIndexOfArray(metCalculator.getIntensityArray(activity.getSport()), activity.getIntensity()));
            binding.inputTime.setText(String.valueOf(activity.getTime()));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("NewActivityFragment",
                "onItemSelected: " + adapterView.getItemAtPosition(i).toString());

        String[] intensity =
                metCalculator.getStringArray(metCalculator.getIntensityArray(adapterView.getItemAtPosition(i).toString()));
        if (intensity == null) {
            intensity = new String[]{getString(R.string.noIntensivity)};
            binding.inputIntensity.setEnabled(false);
        } else binding.inputIntensity.setEnabled(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, intensity);
        binding.inputIntensity.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d("NewActivityFragment",
                "onNothingSelected: " + adapterView.getItemAtPosition(0).toString());

    }
}