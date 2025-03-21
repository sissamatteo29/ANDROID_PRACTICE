package com.msissa.android_practice.data.di

import com.msissa.android_practice.data.settings.SettingsDataSource
import com.msissa.android_practice.data.settings.SettingsDataSourceImpl
import com.msissa.android_practice.data.settings.SettingsRepository
import com.msissa.android_practice.data.settings.SettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsBinderModule {

    @Binds
    abstract fun bindSettingsDataSource(settingsDataSourceImpl: SettingsDataSourceImpl): SettingsDataSource

    @Binds
    abstract fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository

}