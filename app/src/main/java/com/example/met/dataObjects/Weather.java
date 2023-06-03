package com.example.met.dataObjects;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Weather extends ViewModel {

    private final MutableLiveData<String> name = new MutableLiveData<>("Hallo Welt");
    private final MutableLiveData<Double> temp = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> feelsLike = new MutableLiveData<>(0.0);
    private final MutableLiveData<String> icon = new MutableLiveData<>("");
    private final MutableLiveData<Double> clouds = new MutableLiveData<>(0.0);

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<Double> getTemp() {
        return temp;
    }

    public MutableLiveData<Double> getFeelsLike() {
        return feelsLike;
    }

    public MutableLiveData<String> getIcon() {
        return icon;
    }

    public MutableLiveData<Double> getClouds() {
        return clouds;
    }
}
