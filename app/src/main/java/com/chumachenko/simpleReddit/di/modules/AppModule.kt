package com.chumachenko.simpleReddit.di.modules

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun gson(): Gson = Gson()
}