package com.emamagic.moviestreaming.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.emamagic.moviestreaming.util.Const
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Const.PREF_NAME)

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

    fun isLogin(): Flow<Boolean> {
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
    }
}
