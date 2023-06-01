package com.example.met;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.met.dataObjects.Activity;
import com.example.met.dataObjects.Plan;
import com.example.met.databinding.FragmentActivityOverviewBinding;
import com.example.met.databinding.FragmentChoosePlanBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChoosePlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoosePlanFragment extends Fragment implements AdapterView.OnItemClickListener {

    FragmentChoosePlanBinding binding;

    DatabaseHelper db;


    public ChoosePlanFragment() {
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
        db = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentChoosePlanBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Plan[] plans = db.getPlans();
        String[] activityNames = new String[plans.length];
        for (int i = 0; i < plans.length; i++) {
            activityNames[i] = plans[i].getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, activityNames);
        binding.planListView.setAdapter(adapter);
        binding.planListView.setOnItemClickListener(this);


        binding.createPlanButton.setOnClickListener((v) -> {
            int planId = db.insertPlan("New Plan");

            Bundle bundle = new Bundle();
            bundle.putInt("edit_id", planId);
            Navigation.findNavController(view).navigate(R.id.action_choosePlanFragment_to_createPlanFragment, bundle);
        });


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("ActivityOverviewFragment", "onItemClick: " + i + " " + l);
        Bundle bundle = new Bundle();
        bundle.putInt("edit_id", i);
        Navigation.findNavController(view).navigate(R.id.action_choosePlanFragment_to_createPlanFragment, bundle);

    }
}