package com.chumachenko.simpleReddit.data.api.response.newrespose

import android.os.Parcelable
import com.chumachenko.simpleReddit.data.api.response.RedditItem
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
    fun toFeedList(): ArrayList<RedditItem> {
        val newList = arrayListOf<RedditItem>()
        children?.forEach { item ->
            item.toFeedList()?.let { it1 -> newList.add(it1) }
        }
        return newList
    }
}