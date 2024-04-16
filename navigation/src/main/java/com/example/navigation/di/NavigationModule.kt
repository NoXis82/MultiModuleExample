package com.example.navigation.di

import com.example.common_core.navigation.NavigationService
import com.example.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {
    @Provides
    @Singleton
    fun provideNavigationCommander(navigator: Navigator): NavigationService = navigator
}