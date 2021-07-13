package com.chumachenko.simpleReddit.data.repository.model

import android.os.Parcelable
import com.chumachenko.simpleReddit.data.db.realmModel.RedditItemRealm
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RedditItem(
    var id: String,
    var title: String = "",
    var thumbnail: String? = null,
    var uts: Double? = null,
    var after: String?,
    var subreddit: String?,
    var num_comments: Int?,
    var score: Int?,
    var permalink: String?
) : Parcelable {
    fun toRealm() = RedditItemRealm(id,title, thumbnail, uts, after, subreddit, num_comments, score, permalink)
    override fun hashCode(): Int = id.hashCode()
    override fun equals(other: Any?): Boolean =
        if (other is RedditItem) {
            id == other.id
        } else false
}