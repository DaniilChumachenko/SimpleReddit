package com.chumachenko.simpleReddit.data.api.response.newrespose

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class LinkFlairRichtext(
    val a: String?,
    val e: String?,
    val t: String?,
    val u: String?
): Parcelable