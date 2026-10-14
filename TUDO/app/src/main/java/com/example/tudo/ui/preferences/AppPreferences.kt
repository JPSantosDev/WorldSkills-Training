package com.example.tudo.ui.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.tudo.ui.models.C1.AppState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("app_preferences")

class AppPreferences(private val context: Context) {
    object Keys{
        val SPLASHSEEN = booleanPreferencesKey("splash_seen")
        val DARKMODE = booleanPreferencesKey("dark_mode")
    }

    val state: Flow<AppState> = context.dataStore.data
        .map { preferences ->
            AppState(
                darkMode = preferences[Keys.DARKMODE]?: false,
                splashSeen = preferences[Keys.SPLASHSEEN]?:false
            )
        }

    suspend fun toggleDarkMode(){
        context.dataStore.edit { preferences ->
            val atual = preferences[Keys.DARKMODE] ?: false
            preferences[Keys.DARKMODE] = !atual
        }
    }

    suspend fun toggleSplashSeen(){
        context.dataStore.edit { preferences ->
            val atual = preferences[Keys.SPLASHSEEN] ?: false
            preferences[Keys.DARKMODE] = !atual
        }
    }
}