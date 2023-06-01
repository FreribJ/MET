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

import com.example.met.dataObjects.User;
import com.example.met.databinding.FragmentOverviewBinding;
import com.example.met.databinding.FragmentUserCreationBinding;

import java.util.Base64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {

    FragmentOverviewBinding binding;

    DatabaseHelper db;

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

        WebView webView = getView().findViewById(R.id.wetter);
        webView.setWebViewClient(new WebViewClient());
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadUrl("https://www.wetter.de/widget/mini/u1m2g657/L2RldXRzY2hsYW5kL3dldHRlci1lbXNkZXR0ZW4tMTgyMjA4MTguaHRtbA==/");

        User user = db.getUser();
        String name = user == null ? "newUser" : user.getName();
        binding.greeting.setText("Hallo \n" + name + "!");

        binding.showAllActivitys.setOnClickListener((v) -> Navigation.findNavController(view).navigate(R.id.action_overviewFragment_to_activityOverviewFragment));
        binding.addActivity.setOnClickListener((v) -> Navigation.findNavController(view).navigate(R.id.action_overviewFragment_to_newDecisionFragment));

    }
}