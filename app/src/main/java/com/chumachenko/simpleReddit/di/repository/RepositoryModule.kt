package com.chumachenko.simpleReddit.di.repository

import com.chumachenko.simpleReddit.data.api.RedditApi
import com.chumachenko.simpleReddit.data.repository.RedditRepository
import com.chumachenko.simpleReddit.data.repository.repositoryImpl.RedditRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(api: RedditApi): RedditRepository {
        return RedditRepositoryImpl(api)
    }

}