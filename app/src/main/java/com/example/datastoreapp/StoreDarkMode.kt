package com.example.datastoreapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreDarkMode(private val context: Context) {

    companion object { //permite criar membros na classe sem necessidade de criar instancia na hora de aceder

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("DarkMode")
        val DARK_MODE = booleanPreferencesKey("dark_mode")

    }


    val getDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE] ?: false
    }

    suspend fun saveDarkMode(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE] = value

        }

    }
}