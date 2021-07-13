package com.chumachenko.simpleReddit.data.db.realmModel

import com.chumachenko.simpleReddit.data.repository.model.RedditItem
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RedditItemRealm(
    @PrimaryKey
    var id: String = "",
    var title: String = "",
    var thumbnail: String? = "",
    var uts: Double? = 0.0,
    var after: String? = "",
    var subreddit: String? = "",
    var num_comments: Int? = 0,
    var score: Int? = 0,
    var permalink: String? = ""
) : RealmObject() {
    fun toRedditItem() =
        RedditItem(id, title, thumbnail, uts, after, subreddit, num_comments, score, permalink)
}
