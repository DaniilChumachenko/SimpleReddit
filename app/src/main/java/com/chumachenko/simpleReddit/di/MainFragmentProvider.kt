package com.chumachenko.simpleReddit.di

import com.chumachenko.simpleReddit.presentation.fragment.TopRedditFragment
import com.chumachenko.simpleReddit.presentation.fragment.SecondFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentProvider {

    @ContributesAndroidInjector()
    abstract fun provideFirstFragment (): TopRedditFragment

    @ContributesAndroidInjector()
    abstract fun provideSecondFragment(): SecondFragment

}