package com.chumachenko.simpleReddit.data.repository

import com.chumachenko.simpleReddit.data.repository.model.RedditItem
import io.reactivex.Observable
import io.reactivex.Single


interface RedditRepository {

    fun getRedditPostsByType(subredName: String, typeSort: String, lastItem: String?): Single<ArrayList<RedditItem>>

    fun getRedditPostsByTypeAndTime(subredName: String, typeSort: String, timeSort:String): Single<ArrayList<RedditItem>>

    fun getFromLocal(): Observable<ArrayList<RedditItem>>
}