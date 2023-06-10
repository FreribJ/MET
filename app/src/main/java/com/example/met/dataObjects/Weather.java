package com.example.met.dataObjects;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Weather extends ViewModel {

    private final MutableLiveData<String> name = new MutableLiveData<>(null);
    private final MutableLiveData<Double> temp = new MutableLiveData<>(null);
    private final MutableLiveData<String> icon = new MutableLiveData<>(null);

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<Double> getTemp() {
        return temp;
    }

    public MutableLiveData<String> getIcon() {
        return icon;
    }
}
