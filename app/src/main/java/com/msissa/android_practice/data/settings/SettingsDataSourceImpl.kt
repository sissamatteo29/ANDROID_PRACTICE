package com.msissa.android_practice.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.msissa.android_practice.data.settings.SettingsDataSourceImpl.UserPreferences.LANGUAGE
import com.msissa.android_practice.data.settings.SettingsDataSourceImpl.UserPreferences.USER_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataSourceImpl @Inject constructor(private val dataStore: DataStore<Preferences>) : SettingsDataSource {

    /* Object holding the keys for the preferences */
    object UserPreferences {
        val USER_NAME = stringPreferencesKey("pref_username")
        val LANGUAGE = stringPreferencesKey("pref_language")
    }

    /* Methods to manipulate the USER_NAME */
    override fun getUserName(): Flow<String> = getDataString(USER_NAME)
    override suspend fun getUserNameSnapshot(): String = getDataStringSnapshot(USER_NAME, "")
    override suspend fun setUserName(userName: String) = setDataString(USER_NAME, userName)

    /* Methods to manipulate the LANGUAGE */
    override fun getLanguage(): Flow<String> = getDataString(LANGUAGE)
    override suspend fun getLanguageSnapshot(): String = getDataStringSnapshot(LANGUAGE, "en")
    override suspend fun setLanguage(userName: String) = setDataString(LANGUAGE, userName)




    /* Common implementations to get and set String values in the preferences, given the key (and the value) */

    private fun getDataString(key : Preferences.Key<String>): Flow<String> {
        return dataStore.data.catch { exception ->
            if(exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }.map { preferences ->
            preferences[key].orEmpty()      // Return the value if it exists, otherwise return an empty string
        }
    }

    private suspend fun getDataStringSnapshot(key : Preferences.Key<String>, default : String): String {
        return dataStore.data.first()[key] ?: default
    }

    private suspend fun setDataString(key : Preferences.Key<String>, newValue : String) {
        dataStore.edit { preferences ->
            preferences[key] = newValue
        }
    }

}