package com.hilmisatrio.githubuserapp.data.local.dsprefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingAppPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_MODE = booleanPreferencesKey("theme_setting_app")

    fun getThemeMode(): Flow<Boolean> {
        return dataStore.data.map {
            it[THEME_MODE] ?: false
        }
    }

    suspend fun saveThemeMode(isDarkMode: Boolean) {
        dataStore.edit {
            it[THEME_MODE] = isDarkMode
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingAppPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): SettingAppPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingAppPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}