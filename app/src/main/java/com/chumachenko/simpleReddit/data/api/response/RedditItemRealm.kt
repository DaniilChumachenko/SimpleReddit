package com.chumachenko.simpleReddit.data.api.response

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RedditItemRealm (
        @PrimaryKey
        var id: String = "",
        var title: String = "",
        var tubnail: String? = "",
        var uts: Double? = 0.0
): RealmObject() {
    fun toRealm() = RedditItemRealm(id, title,tubnail,uts)
    fun toRedditItem() = RedditItem(id, title,tubnail,uts)
}
