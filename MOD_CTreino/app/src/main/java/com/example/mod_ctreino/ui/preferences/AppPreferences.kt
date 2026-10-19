package com.example.mod_ctreino.ui.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.mod_ctreino.ui.model.AppState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("app_preferences")

class AppPreferences(val context: Context) {
    object Keys{
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val SPLASH_SEEN = booleanPreferencesKey("splash_seen")
        val HIGH_CONTRAST = booleanPreferencesKey("high_contrast")
        val NOTIFICATIONS_ACTIVE = booleanPreferencesKey("notifications_active")
    }

    val state: Flow<AppState> = context.dataStore.data
        .map { preferences ->
            AppState(
                darkMode = preferences[Keys.DARK_MODE] ?: false,
                splashSeen = preferences[Keys.SPLASH_SEEN] ?: false,
                highContrast = preferences[Keys.HIGH_CONTRAST] ?: false,
                notificationActive = preferences[Keys.NOTIFICATIONS_ACTIVE] ?: false,

                )
        }

    suspend fun toggleDarkMode(){
        context.dataStore.edit { preferences ->
            val atual = preferences[Keys.DARK_MODE] ?: false
            preferences[Keys.DARK_MODE] = !atual
        }
    }
    suspend fun toggleSplashSeen(){
        context.dataStore.edit { preferences ->
            val atual = preferences[Keys.SPLASH_SEEN] ?: false
            preferences[Keys.SPLASH_SEEN] = !atual
        }
    }
    suspend fun toggleHighContrast(){
        context.dataStore.edit { preferences ->
            val atual = preferences[Keys.HIGH_CONTRAST] ?: false
            preferences[Keys.HIGH_CONTRAST] = !atual
        }
    }
    suspend fun toggleNotificationsActive(){
        context.dataStore.edit { preferences ->
            val atual = preferences[Keys.NOTIFICATIONS_ACTIVE] ?: false
            preferences[Keys.NOTIFICATIONS_ACTIVE] = !atual
        }
    }


}