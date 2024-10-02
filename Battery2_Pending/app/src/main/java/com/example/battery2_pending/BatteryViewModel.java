package com.example.battery2_pending;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BatteryViewModel extends ViewModel {

    private final MutableLiveData<Integer> batteryLevel = new MutableLiveData<>();

    public BatteryViewModel() {
        batteryLevel.setValue(-1);
    }

    public LiveData<Integer> getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int level) {
        batteryLevel.setValue(level);
    }
}
