package com.chumachenko.simpleReddit.data.repository.repositoryImpl

import com.chumachenko.simpleReddit.data.api.RedditApi
import com.chumachenko.simpleReddit.data.api.response.RedditItem
import com.chumachenko.simpleReddit.data.db.RedditLocalSource
import com.chumachenko.simpleReddit.data.repository.RedditRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RedditRepositoryImpl(
    private val api: RedditApi,
    private val redditDataSource: RedditLocalSource
) : RedditRepository {

    override fun getRedditPostsByType(subredName: String, typeSort: String): Single<ArrayList<RedditItem>> =
        api.getSubredditPostsByType(subredName, typeSort, null)
            .subscribeOn(Schedulers.io())
            .map {
                redditDataSource.addOrReplace(it.toRealm())
                it.toRedditItem()
            }

    override fun getRedditPostsByTypeAndTime(
        subredName: String,
        typeSort: String,
        timeSort: String
    ): Single<ArrayList<RedditItem>> =
        api.getSubredditPostsByTypeAndTime(subredName, typeSort,timeSort, null)
            .subscribeOn(Schedulers.io())
            .map {
                redditDataSource.addOrReplace(it.toRealm())
                it.toRedditItem()
            }
}