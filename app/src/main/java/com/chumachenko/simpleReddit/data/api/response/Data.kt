package com.chumachenko.simpleReddit.data.api.response

import android.os.Parcelable
import com.chumachenko.simpleReddit.data.db.realmModel.RedditItemRealm
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Data(
    @SerializedName("created_utc") val created_utc: Double?,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("num_comments") val num_comments: Int?,
    @SerializedName("score") val score: Int?,
    @SerializedName("subreddit_name_prefixed") val subreddit: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("title") val title: String,
    @SerializedName("permalink") val permalink: String?
) : Parcelable {

    fun toRealm(): RedditItemRealm = RedditItemRealm(
        id,
        title,
        thumbnail,
        created_utc,
        null,
        subreddit,
        num_comments,
        score,
        permalink
    )
}