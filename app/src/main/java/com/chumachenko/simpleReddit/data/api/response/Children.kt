package com.chumachenko.simpleReddit.data.api.response

import android.os.Parcelable
import com.chumachenko.simpleReddit.data.db.realmModel.RedditItemRealm
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Children(
    @SerializedName("data") val data: Data?
) : Parcelable {
    fun toRealm(): RedditItemRealm? = data?.toRealm()
}