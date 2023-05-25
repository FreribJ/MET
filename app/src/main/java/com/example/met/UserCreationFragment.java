package com.example.met;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.met.databinding.FragmentUserCreationBinding;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserCreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserCreationFragment extends Fragment {

    private FragmentUserCreationBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserCreationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserCreationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserCreationFragment newInstance(String param1, String param2) {
        UserCreationFragment fragment = new UserCreationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        binding.userCreationFinished.setOnClickListener((v) -> {
        try {
            createUser();
            Navigation.findNavController(view).navigate(R.id.overviewFragment);
        } catch (Exception e) {

            System.out.println("Eingabe fehlgeschlagen");
            throw e;
        }
        });


    }

    void createUser() {
        String name = binding.inputName.getText().toString().strip();
        if (name == "")
            throw new InputMismatchException();

        int age = Integer.parseInt(binding.inputAge.getText().toString());
        if (age < 0 || age > 120)
            throw new InputMismatchException();

        double weight = Double.parseDouble(binding.inputWeight.getText().toString());
        if (weight < 0 || weight > 500)
            throw new InputMismatchException();

        String category = "test";
//        String category = binding.categoryChoice.getTransitionName();
//        if (category == "")
//            throw new InputMismatchException();

        try {
            JSONObject userConfig = new JSONObject();
            userConfig.put("name", name);
            userConfig.put("age", age);
            userConfig.put("weight", weight);
            userConfig.put("category", category);

            System.out.println(userConfig.toString());

//            File file = new File("config.json");
            OutputStreamWriter writer = new OutputStreamWriter(getContext().openFileOutput("config.json", Context.MODE_PRIVATE));
            writer.write(userConfig.toString());
            writer.close();
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}