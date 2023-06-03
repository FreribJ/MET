package com.example.met;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.met.dataObjects.Activity;
import com.example.met.dataObjects.User;
import com.example.met.dataObjects.Weather;
import com.example.met.databinding.FragmentOverviewBinding;
import com.example.met.met.MetCalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {

    Weather weather;

    FragmentOverviewBinding binding;

    DatabaseHelper db;

    MetCalculator metCalculator = new MetCalculator();

    public static OverviewFragment newInstance(String param1, String param2) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
        weather = new ViewModelProvider(requireActivity()).get(Weather.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOverviewBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        weather.getTemp().observe(requireActivity(),
                (temp) -> binding.wetter.setText(Double.toString(temp)));

        User user = db.getUser();
        String name = user == null ? "newUser" : user.getName();
        binding.greeting.setText("Hallo \n" + name + "!");

        binding.showAllActivitys.setOnClickListener((v) -> Navigation.findNavController(view).navigate(R.id.action_overviewFragment_to_activityOverviewFragment));
        binding.addActivity.setOnClickListener((v) -> Navigation.findNavController(view).navigate(R.id.action_overviewFragment_to_newDecisionFragment));

        //Stats:
        TextView test = binding.secondOverviewStat.title;
        test.setText("Hat das Funktioniert?");
        showStatistics();
    }

    void showStatistics() {
        User user = db.getUser();
        if (user == null) {
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); //dd.MM.yyyy
        Date today = new Date();

        Activity[] activities = db.getActivities();

        int activitiesToday = 0;
        double metValueToday = 0;
        int activitiesThisWeek = 0;
        double metValueThisWeek = 0;

        for (Activity activity : activities) {
            try {
                long ago = Duration.between(today.toInstant(),
                        formatter.parse(activity.getDate()).toInstant()).toDays();
                if (ago == 0) {
                    activitiesToday++;
                    metValueToday += metCalculator.getMet(activity);
                }
                if (ago > -7 && ago <= 0) {
                    activitiesThisWeek++;
                    metValueThisWeek += metCalculator.getMet(activity);
                }
            } catch (ParseException e) {
                Log.d("Statistics", "ParseException");
            }
        }

        binding.firstOverviewStat.title.setText("Heute");
        binding.firstOverviewStat.acutalMet.setText(binding.firstOverviewStat.acutalMet.getText().toString().replace("${value}", String.valueOf(metValueToday)));
        binding.firstOverviewStat.neededMet.setText(binding.firstOverviewStat.neededMet.getText().toString().replace("${value}", String.valueOf(metCalculator.getCategory(user.getCategory()).getTo() - metValueToday)));

        binding.secondOverviewStat.title.setText("Diese Woche");
        binding.secondOverviewStat.acutalMet.setText(binding.secondOverviewStat.acutalMet.getText().toString().replace("${value}", String.valueOf(metValueThisWeek)));
        binding.secondOverviewStat.neededMet.setText(binding.secondOverviewStat.neededMet.getText().toString().replace("${value}", String.valueOf(metCalculator.getCategory(user.getCategory()).getTo() * 7 - metValueThisWeek)));

    }
}