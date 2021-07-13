package com.chumachenko.simpleReddit.di.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun gson(): Gson = Gson()
}