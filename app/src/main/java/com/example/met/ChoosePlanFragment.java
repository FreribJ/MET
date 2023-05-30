package com.example.met;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.met.databinding.FragmentActivityOverviewBinding;
import com.example.met.databinding.FragmentChoosePlanBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChoosePlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoosePlanFragment extends Fragment {

    FragmentChoosePlanBinding binding;


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


        binding.usePlanButton.setOnClickListener((v) -> Navigation.findNavController(view).navigate(R.id.action_overviewFragment_to_newDecisionFragment));
        binding.createPlanButton.setOnClickListener((v) -> Navigation.findNavController(view).navigate(R.id.action_choosePlanFragment_to_createPlanFragment));

    }
}