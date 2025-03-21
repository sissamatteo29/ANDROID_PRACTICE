package com.msissa.android_practice.data.settings

import androidx.preference.PreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SettingsPreferenceDataStore @Inject constructor(private val settingsRepository: SettingsRepository) : PreferenceDataStore() {

    override fun putString(key: String?, value: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            when(key) {
                "pref_username" -> settingsRepository.setUserName(value ?: "")
                "pref_language" -> settingsRepository.setLanguage(value ?: "en")
                else -> {}
            }
        }
    }

    override fun getString(key: String?, defValue: String?): String {
        var result: String?
        runBlocking(Dispatchers.IO) {
            result = when(key) {
                "pref_username" -> settingsRepository.getUserNameSnapshot()
                "pref_language" -> settingsRepository.getLanguageSnapshot()
                else -> defValue
            }
        }
        return result ?: defValue ?: ""
    }


}