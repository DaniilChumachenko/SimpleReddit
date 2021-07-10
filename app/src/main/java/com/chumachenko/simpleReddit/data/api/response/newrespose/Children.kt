package com.chumachenko.simpleReddit.data.api.response.newrespose

import android.os.Parcelable
import com.chumachenko.simpleReddit.data.api.response.RedditItem
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Children(
    @SerializedName("data") val data: DataX?,
    @SerializedName("kind") val kind: String?
): Parcelable
{
    fun toFeedList(): RedditItem? = data?.toFeedList()

}