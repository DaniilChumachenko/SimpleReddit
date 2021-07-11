package com.chumachenko.simpleReddit.data.api.response

import android.os.Parcelable
import com.chumachenko.simpleReddit.data.db.realmModel.RedditItemRealm
import com.chumachenko.simpleReddit.data.repository.model.RedditItem
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RedditItemResponse(
    @SerializedName("data") val coreData: CoreData
) : Parcelable {
    fun toRealm(): ArrayList<RedditItemRealm> =
        coreData.toRealm()

    fun toRedditItem(): ArrayList<RedditItem> =
        coreData.toRedditItem()
}