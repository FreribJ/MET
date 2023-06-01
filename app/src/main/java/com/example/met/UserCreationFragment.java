package com.example.met;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.met.dataObjects.User;
import com.example.met.databinding.FragmentUserCreationBinding;
import com.example.met.met.HasName;
import com.example.met.met.MetCalculator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class UserCreationFragment extends Fragment {

    private FragmentUserCreationBinding binding;

    DatabaseHelper db;

    public UserCreationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserCreationBinding.inflate(inflater);


        return binding.getRoot();

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_user_creation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Fill Spinner with categories
        MetCalculator metCalculator = new MetCalculator();
        String[] categories = metCalculator.getStringArray(metCalculator.getCategoryArray());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        binding.categoryChoice.setAdapter(adapter);

        //Fill fields with user data
        User user = db.getUser();
        if (user != null) {
            Log.d("User", user.toString());
            binding.inputName.setText(user.getName());
            binding.inputAge.setText(String.valueOf(user.getAge()));
            binding.inputWeight.setText(String.valueOf(user.getWeight()));
            binding.categoryChoice.setSelection(metCalculator.getIndexOfArray(metCalculator.getStringArray(metCalculator.getCategoryArray()), user.getCategory()));
        }

        binding.userCreationFinished.setOnClickListener((v) -> {
        try {
            createUser(user != null);
            Navigation.findNavController(view).navigate(R.id.overviewFragment);
        } catch (Exception e) {
            System.out.println("Eingabe fehlgeschlagen: " + e.getMessage());
        }
        });


    }

    void createUser(boolean update) {
        String name = binding.inputName.getText().toString().strip();
        if (name == "")
            throw new InputMismatchException();

        int age = Integer.parseInt(binding.inputAge.getText().toString());
        if (age < 0 || age > 120)
            throw new InputMismatchException();

        double weight = Double.parseDouble(binding.inputWeight.getText().toString());
        if (weight < 0 || weight > 500)
            throw new InputMismatchException();

        String category = binding.categoryChoice.getSelectedItem().toString();
        if (category == "")
            throw new InputMismatchException();

        if (update)
            db.updateUser(name, age, weight, category);
        else
            db.insertUser(name, age, weight, category);

    }
}