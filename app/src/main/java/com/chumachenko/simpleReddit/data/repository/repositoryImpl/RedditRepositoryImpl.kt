package com.chumachenko.simpleReddit.data.repository.repositoryImpl

import com.chumachenko.simpleReddit.data.api.RedditApi
import com.chumachenko.simpleReddit.data.api.response.newrespose.RedditItemResponse
import com.chumachenko.simpleReddit.data.repository.RedditRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RedditRepositoryImpl(
    private val api: RedditApi
) : RedditRepository {


    override fun getFeedPosts(subredName:String, typeSort:String): Single<RedditItemResponse> =
        api.getSubredditBestPosts(subredName, typeSort,"week",null)
}