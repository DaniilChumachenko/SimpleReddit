package com.chumachenko.simpleReddit.data.api.response.newrespose

import android.os.Parcelable
import com.chumachenko.simpleReddit.data.api.response.RedditItem
import com.chumachenko.simpleReddit.data.api.response.RedditItemRealm
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RedditItemResponse(
    @SerializedName("data") val data: Data,
    @SerializedName("kind") val kind: String
) : Parcelable {
    fun toRealm(): ArrayList<RedditItemRealm> =
        data.toRealm()

    fun toRedditItem(): ArrayList<RedditItem> =
        data.toRedditItem()
}