package com.hilmisatrio.githubuserapp.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hilmisatrio.githubuserapp.data.local.dsprefs.SettingAppPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingAppPreferences) : ViewModel() {
    fun getThemeMode(): LiveData<Boolean> {
        return pref.getThemeMode().asLiveData()
    }

    fun saveThemeMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            pref.saveThemeMode(isDarkMode)
        }
    }
}