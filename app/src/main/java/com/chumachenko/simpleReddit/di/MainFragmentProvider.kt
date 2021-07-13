package com.chumachenko.simpleReddit.di

import com.chumachenko.simpleReddit.presentation.fragment.TopRedditFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentProvider {

    @ContributesAndroidInjector()
    abstract fun provideTopRedditFragment (): TopRedditFragment

}