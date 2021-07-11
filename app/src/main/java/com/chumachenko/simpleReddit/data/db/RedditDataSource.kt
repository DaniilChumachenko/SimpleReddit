package com.chumachenko.simpleReddit.data.db

import com.chumachenko.simpleReddit.data.db.realmModel.RedditItemRealm
import io.reactivex.Maybe

interface RedditDataSource<M, L> : LocalDataSource<M, L> {

    fun findByIdSync(id: String): L?

    fun search(search: String, isFollowing: Boolean): Maybe<List<L>>

    fun deleteSync(id: String)

    fun findByIdRealmObject(itemId: String): M?

    fun addOrReplaceSync(model: RedditItemRealm): L
}