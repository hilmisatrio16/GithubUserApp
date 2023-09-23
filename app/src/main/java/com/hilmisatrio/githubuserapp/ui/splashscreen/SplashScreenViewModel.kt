package com.hilmisatrio.githubuserapp.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hilmisatrio.githubuserapp.data.local.dsprefs.SettingAppPreferences

class SplashScreenViewModel(private val pref: SettingAppPreferences) : ViewModel() {
    fun getThemeMode(): LiveData<Boolean> {
        return pref.getThemeMode().asLiveData()
    }
}