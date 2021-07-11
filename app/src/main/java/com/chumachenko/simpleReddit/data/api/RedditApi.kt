package com.chumachenko.simpleReddit.data.api

import com.chumachenko.simpleReddit.data.api.response.RedditItemResponse
import io.reactivex.Single
import retrofit2.http.*

interface RedditApi {
    @GET("r/{subredditName}/{sortType}.json?raw_json=1&limit=10")
    fun getSubredditPostsByType(
        @Path("subredditName") subredditName: String?,
        @Path("sortType") sortType: String?,
        @Query("after") lastItem: String?
    ): Single<RedditItemResponse>

    @GET("r/{subredditName}/{sortType}.json?raw_json=1&limit=23")
    fun getSubredditPostsByTypeAndTime(
        @Path("subredditName") subredditName: String?, @Path("sortType") sortType: String?,
        @Query("t") sortTime: String?, @Query("after") lastItem: String?
    ): Single<RedditItemResponse>
}