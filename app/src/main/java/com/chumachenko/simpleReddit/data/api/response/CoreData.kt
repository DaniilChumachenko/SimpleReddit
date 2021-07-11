package com.chumachenko.simpleReddit.data.api.response

import android.os.Parcelable
import com.chumachenko.simpleReddit.data.db.realmModel.RedditItemRealm
import com.chumachenko.simpleReddit.data.repository.model.RedditItem
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class CoreData(
    @SerializedName("after") val after: String?,
    @SerializedName("children") val children: List<Children>?,
) : Parcelable {
    fun toRealm(): ArrayList<RedditItemRealm> {
        val newList = arrayListOf<RedditItemRealm>()
        children?.forEach { item ->
            item.toRealm()?.let { itemRealm ->
                itemRealm.after = after
                newList.add(itemRealm)
            }
        }
        return newList
    }

    fun toRedditItem(): ArrayList<RedditItem> {
        val newList = arrayListOf<RedditItem>()
        children?.forEach { item ->
            item.toRealm()?.let { it ->
                it.after = after
                newList.add(it.toRedditItem())
            }
        }
        return newList
    }
}