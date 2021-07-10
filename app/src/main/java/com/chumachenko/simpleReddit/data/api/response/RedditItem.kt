package com.chumachenko.simpleReddit.data.api.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RedditItem(
        var id: String,
        var title: String = "",
        var tubnail: String? = null,
        var uts: Double? = null
        ) : Parcelable{
//    fun toRealm() : SaveListRealm {
//        return SaveListRealm(id, listName, itemId, stepsCount, postCount, mapId, stepId, typeList)
//    }

    override fun hashCode(): Int = id.hashCode()
    override fun equals(other: Any?): Boolean =
            if (other is RedditItem) {
                id == other.id
            } else false
}