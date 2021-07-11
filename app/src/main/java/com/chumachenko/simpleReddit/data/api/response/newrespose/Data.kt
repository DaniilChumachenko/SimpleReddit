package com.chumachenko.simpleReddit.data.api.response.newrespose

import android.os.Parcelable
import com.chumachenko.simpleReddit.data.api.response.RedditItem
import com.chumachenko.simpleReddit.data.api.response.RedditItemRealm
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Data(
    @SerializedName("after") val after: String?,
    @SerializedName("before") val before: String?,
    @SerializedName("children") val children: List<Children>?,
    @SerializedName("dist") val dist: Int?,
    @SerializedName("geo_filter") val geo_filter: String?,
    @SerializedName("modhash") val modhash: String?
): Parcelable {
    fun toRealm(): ArrayList<RedditItemRealm> {
        val newList = arrayListOf<RedditItemRealm>()
        children?.forEach { item ->
            item.toRealm()?.let { itemRealm -> newList.add(itemRealm) }
        }
        return newList
    }
    fun toRedditItem(): ArrayList<RedditItem> {
        val newList = arrayListOf<RedditItem>()
        children?.forEach { item ->
            item.toRealm()?.let { it -> newList.add(it.toRedditItem()) }
        }
        return newList
    }
}