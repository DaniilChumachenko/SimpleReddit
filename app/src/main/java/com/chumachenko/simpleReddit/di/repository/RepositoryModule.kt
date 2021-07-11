package com.chumachenko.simpleReddit.di.repository

import com.chumachenko.simpleReddit.data.api.RedditApi
import com.chumachenko.simpleReddit.data.db.RedditLocalSource
import com.chumachenko.simpleReddit.data.repository.RedditRepository
import com.chumachenko.simpleReddit.data.repository.repositoryImpl.RedditRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRedditRepository(
        api: RedditApi,
        redditSource: RedditLocalSource,
        gson: Gson
    ): RedditRepository {
        return RedditRepositoryImpl(
            api,
            redditSource
        )
    }
}