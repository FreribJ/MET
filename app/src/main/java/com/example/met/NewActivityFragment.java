package com.example.met;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.met.dataObjects.Activity;
import com.example.met.databinding.FragmentNewActivityBinding;
import com.example.met.met.MetCalculator;

import java.util.Date;


public class NewActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String ARG_PARAM_ACTIVITY_ID = "activity_id";

    int activityId = -1;

    FragmentNewActivityBinding binding;

    MetCalculator metCalculator = new MetCalculator();

    DatabaseHelper db;

    public NewActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            activityId = getArguments().getInt(ARG_PARAM_ACTIVITY_ID);
        }

        db = new DatabaseHelper(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewActivityBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] sports = metCalculator.getStringArray(metCalculator.getSportArray());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sports);
        binding.inputSport.setAdapter(adapter);
        binding.inputSport.setOnItemSelectedListener(this);

        binding.removeActivityButton.setOnClickListener(view1 -> {
            db.deleteActivity(activityId);
            Navigation.findNavController(view).popBackStack();
        });

        binding.activityCreationFinished.setOnClickListener(view1 -> {
            String name = binding.inputName.getText().toString();
            String sport = binding.inputSport.getSelectedItem().toString();
            String intensity = binding.inputIntensity.getSelectedItem().toString();
            double duration = Double.parseDouble(binding.inputTime.getText().toString());
            String date = binding.inputDate.getText().toString();

            Log.d("NewActivityFragment", "onViewCreated: " + name + " " + sport + " " + intensity + " " + duration + " " + date);

            if (activityId != -1)
                db.updateActivity(activityId, name, sport, intensity, duration, date);
            else
                db.insertActivity(name, sport, intensity, duration, date, db.getUser().getWeight());

            Navigation.findNavController(view).popBackStack(R.id.activityOverviewFragment, false);
        });

        if (activityId != -1) {
            Activity activity = db.getActivity(activityId);
            binding.inputName.setText(activity.getName());
            binding.inputSport.setSelection(metCalculator.getIndexOfArray(metCalculator.getSportArray(), activity.getSport()));
            binding.inputIntensity.setSelection(metCalculator.getIndexOfArray(metCalculator.getIntensityArray(activity.getSport()), activity.getIntensity()));
            binding.inputTime.setText(String.valueOf(activity.getTime()));
            binding.inputDate.setText(activity.getDate());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("NewActivityFragment", "onItemSelected: " + adapterView.getItemAtPosition(i).toString());

        String[] intensity = metCalculator.getStringArray(metCalculator.getIntensityArray(adapterView.getItemAtPosition(i).toString()));
        if (intensity == null) {
            intensity = new String[]{""};
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, intensity);
        binding.inputIntensity.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d("NewActivityFragment", "onNothingSelected: " + adapterView.getItemAtPosition(0).toString());

    }
}