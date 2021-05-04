package com.emamagic.moviestreaming.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext val context: Context) {

    private val TAG = "PreferencesManager"

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "movie_preferences")

    suspend fun setUserName(userName: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = userName
        }
    }

    suspend fun setUserEmail(userEmail: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_EMAIL] = userEmail
        }
    }

    suspend fun setUserPhone(userPhone: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_PHONE] = userPhone
        }
    }

    suspend fun isLogin(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.USER_EMAIL] != null &&
                preferences[PreferencesKeys.USER_PHONE] != null

            }
    }

    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_PHONE = stringPreferencesKey("user_phone")
        val REMEMBER_ME = booleanPreferencesKey("remembered")
        val IS_REMEMBERED = booleanPreferencesKey("is_remembered")
    }
}
