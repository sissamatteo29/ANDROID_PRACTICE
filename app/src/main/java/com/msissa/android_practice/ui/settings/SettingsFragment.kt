package com.msissa.android_practice.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.msissa.android_practice.R
import com.msissa.android_practice.data.settings.SettingsPreferenceDataStore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var settingsPreferenceDataStore : SettingsPreferenceDataStore

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        // Setup the SettingsPreferenceDataStore to manage the UI modifications to the settings
        preferenceManager.preferenceDataStore = settingsPreferenceDataStore

        // Load the preferences from the XML file (UI components)
        setPreferencesFromResource(R.xml.preferences_settings, rootKey)
    }

}

