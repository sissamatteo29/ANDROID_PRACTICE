package com.msissa.android_practice.data.settings

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val settingsDataSource: SettingsDataSource) : SettingsRepository {

    override fun getUserName(): Flow<String> {
        return settingsDataSource.getUserName()
    }

    override suspend fun getUserNameSnapshot(): String {
        return settingsDataSource.getUserNameSnapshot()
    }

    override suspend fun setUserName(userName: String) {
        return settingsDataSource.setUserName(userName)
    }


    override fun getLanguage(): Flow<String> {
        return settingsDataSource.getLanguage()
    }

    override suspend fun getLanguageSnapshot(): String {
        return settingsDataSource.getLanguageSnapshot()
    }

    override suspend fun setLanguage(userName: String) {
        return settingsDataSource.setLanguage(userName)
    }


}