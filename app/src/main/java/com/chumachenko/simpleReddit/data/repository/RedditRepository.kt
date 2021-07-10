package com.chumachenko.simpleReddit.data.repository

import com.chumachenko.simpleReddit.data.api.response.newrespose.RedditItemResponse
import io.reactivex.Single


interface RedditRepository {

    fun getFeedPosts(subredName: String, typeSort: String): Single<RedditItemResponse>
}