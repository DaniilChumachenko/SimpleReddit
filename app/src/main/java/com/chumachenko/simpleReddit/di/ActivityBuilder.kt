package com.chumachenko.simpleReddit.di

import com.chumachenko.simpleReddit.presentation.activity.MainActivity
import com.chumachenko.simpleReddit.presentation.activity.WelcomeScreen
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    internal abstract fun bindWelcomeScreen(): WelcomeScreen

    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity

}