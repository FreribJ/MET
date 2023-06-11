package com.example.met;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.met.dataObjects.User;
import com.example.met.databinding.FragmentUserCreationBinding;
import com.example.met.met.MetCalculator;

import java.util.InputMismatchException;

public class UserCreationFragment extends Fragment {

    DatabaseHelper db;
    private FragmentUserCreationBinding binding;

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories);
        binding.categoryChoice.setAdapter(adapter);

        //Fill fields with user data
        User user = db.getUser();
        if (user != null) {
            binding.inputName.setText(user.getName());
            binding.inputAge.setText(String.valueOf(user.getDateOfBirth()));
            binding.inputWeight.setText(String.valueOf(user.getWeight()));
            binding.categoryChoice.setSelection(metCalculator.getIndexOfArray(metCalculator.getStringArray(metCalculator.getCategoryArray()), user.getCategory()));
        }

        binding.userCreationFinished.setOnClickListener((v) -> {
            try {
                createUser(user != null);
                Navigation.findNavController(view).navigate(R.id.overviewFragment);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Bitte fülle alle Felder richtig aus",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    void createUser(boolean update) {
        String name = binding.inputName.getText().toString().strip();
        if ("".equals(name)) throw new InputMismatchException();

        String dateOfBirth = binding.inputAge.getText().toString();
        if (dateOfBirth.length() != 10) throw new InputMismatchException();

        double weight = Double.parseDouble(binding.inputWeight.getText().toString().replace(',',
                '.'));
        if (weight < 0.0 || weight > 500.0) throw new InputMismatchException();

        String category = binding.categoryChoice.getSelectedItem().toString();
        if ("".equals(category) || "Aktivitätskategorie".equals(category))
            throw new InputMismatchException();

        if (update) db.updateUser(name, dateOfBirth, weight, category);
        else db.insertUser(name, dateOfBirth, weight, category);

    }
}