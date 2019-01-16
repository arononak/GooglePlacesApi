package com.example.googleplacesautocomplete.di

import com.example.googleplacesautocomplete.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity
}