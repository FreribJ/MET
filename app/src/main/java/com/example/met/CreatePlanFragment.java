package com.example.met;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.met.databinding.FragmentCreatePlanBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePlanFragment extends Fragment {

    FragmentCreatePlanBinding binding;


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

        binding.addActivityButton.setOnClickListener((v) -> Navigation.findNavController(view).navigate(R.id.action_createPlanFragment_to_newActivityForPlanFragment));

    }
}