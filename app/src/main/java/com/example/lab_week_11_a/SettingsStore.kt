package com.example.lab_week_11_a

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.lab_week_11_a.PreferenceWrapper.Companion.KEY_TEXT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//ini datastorenya
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settingsStore")

class SettingsStore (private val context: Context){
    //text flow used to notify the viewModel when the text changes
    val text: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_TEXT]?: ""
        }
    //save the text to the datastore
    suspend fun saveText(text: String){
        context.dataStore.edit { preferences ->
            preferences[KEY_TEXT] = text
        }
    }

    //key used to access the data in the datastore
    companion object {
        val KEY_TEXT = stringPreferencesKey("key_text")
    }
}