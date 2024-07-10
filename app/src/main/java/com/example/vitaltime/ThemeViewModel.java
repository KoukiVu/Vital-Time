package com.example.vitaltime;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ThemeViewModel extends ViewModel {
    private final MutableLiveData<Integer> currentTheme = new MutableLiveData<Integer>();

    ThemeViewModel() {
        currentTheme.setValue(R.style.Theme_VitalTime);
    }

    public void setTheme(int themeId) {
        if (currentTheme.getValue() == null || currentTheme.getValue() != themeId) {
            currentTheme.setValue(themeId);
        }
    }

    public MutableLiveData<Integer> getCurrentTheme() {
        return currentTheme;
    }
}
