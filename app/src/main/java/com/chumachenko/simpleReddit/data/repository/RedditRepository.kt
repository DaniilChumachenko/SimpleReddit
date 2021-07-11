package com.chumachenko.simpleReddit.data.repository

import com.chumachenko.simpleReddit.data.api.response.RedditItem
import com.chumachenko.simpleReddit.data.api.response.newrespose.RedditItemResponse
import io.reactivex.Single


interface RedditRepository {

    fun getRedditPostsByType(subredName: String, typeSort: String): Single<ArrayList<RedditItem>>

    fun getRedditPostsByTypeAndTime(subredName: String, typeSort: String, timeSort:String): Single<ArrayList<RedditItem>>
}