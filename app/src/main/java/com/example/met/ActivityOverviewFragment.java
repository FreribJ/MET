package com.example.met;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.met.dataObjects.Activity;
import com.example.met.dataObjects.Weather;
import com.example.met.databinding.FragmentActivityOverviewBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityOverviewFragment extends Fragment implements AdapterView.OnItemClickListener {

    private Weather weather;
    private FragmentActivityOverviewBinding binding;

    private DatabaseHelper db;


    public ActivityOverviewFragment() {
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
        weather = new ViewModelProvider(requireActivity()).get(Weather.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentActivityOverviewBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        weather.getTemp().observe(requireActivity(), (temp) -> {
            if (temp != null) {
                int celsius = (int) (temp - 273.15);
                if(getActivity() != null) {
                    binding.headline.temperatureTextView.setText(getActivity().getString(R.string.temperature,
                            Integer.toString(celsius)));
                }
            }
        });

        weather.getName().observe(requireActivity(), (name) -> {
            if (name != null) {
                binding.headline.locationTextView.setText(name);
            }
        });

        weather.getIcon().observe(requireActivity(), (icon) -> {
            if (icon != null) {
                switch (icon) {
                    case "01d":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_01d);
                        break;
                    case "01n":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_01n);
                        break;
                    case "02d":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_02d);
                        break;
                    case "02n":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_02n);
                        break;
                    case "03d":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_03d);
                        break;
                    case "03n":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_03n);
                        break;
                    case "04d":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_04d);
                        break;
                    case "04n":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_04n);
                        break;
                    case "09d":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_09d);
                        break;
                    case "09n":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_09n);
                        break;
                    case "10d":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_10d);
                        break;
                    case "10n":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_10n);
                        break;
                    case "11d":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_11d);
                        break;
                    case "11n":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_11n);
                        break;
                    case "13d":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_13d);
                        break;
                    case "13n":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_13n);
                        break;
                    case "50d":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_50d);
                        break;
                    case "50n":
                        binding.headline.weatherIcon.setImageResource(R.drawable.weather_50n);
                        break;
                    default:
                        Log.d("WeatherIcon", "Icon not found: " + icon);
                        break;
                }
            }
        });

        Activity[] activities = db.getActivities();
        String[] activityNames = new String[activities.length];
        for (int i = 0; i < activities.length; i++) {
            activityNames[i] = activities[i].getName();
        }
        ActivityOverviewItemAdapter adapter = new ActivityOverviewItemAdapter(getContext(),
                android.R.layout.simple_spinner_item, activities);
        binding.activityList.setAdapter(adapter);
        binding.activityList.setOnItemClickListener(this);

        Bundle bundle = new Bundle();
        bundle.putInt("activity_id", -1);
        binding.addActivity.setOnClickListener((v) -> Navigation.findNavController(view).navigate(R.id.action_activityOverviewFragment_to_newDecisionFragment, bundle));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("ActivityOverviewFragment", "onItemClick: " + i + " " + l);
        Bundle bundle = new Bundle();
        bundle.putInt("activity_id", db.getActivityId(i));
        Navigation.findNavController(view).navigate(R.id.action_activityOverviewFragment_to_newActivityFragment, bundle);
    }
}