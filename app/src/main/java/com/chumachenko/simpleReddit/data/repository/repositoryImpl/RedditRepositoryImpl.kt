package com.chumachenko.simpleReddit.data.repository.repositoryImpl

import com.chumachenko.simpleReddit.data.api.RedditApi
import com.chumachenko.simpleReddit.data.repository.model.RedditItem
import com.chumachenko.simpleReddit.data.db.RedditLocalSource
import com.chumachenko.simpleReddit.data.db.realmModel.RedditItemRealm
import com.chumachenko.simpleReddit.data.repository.RedditRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RedditRepositoryImpl(
    private val api: RedditApi,
    private val redditDataSource: RedditLocalSource
) : RedditRepository {

    override fun getRedditPostsByType(
        subredName: String,
        typeSort: String,
        lastItem: String?
    ): Single<ArrayList<RedditItem>> =
        api.getSubredditPostsByType(subredName, typeSort, lastItem)
            .subscribeOn(Schedulers.io())
            .map {
                redditDataSource.addOrReplace(it.toRealm())
                it.toRedditItem()
            }

    override fun getFromLocal(): Observable<ArrayList<RedditItem>> =
        redditDataSource.findAll().toObservable()

    override fun clearStorage(listItem:ArrayList<RedditItemRealm>): Observable<ArrayList<RedditItem>> =
        redditDataSource.delete(listItem).toObservable()
}