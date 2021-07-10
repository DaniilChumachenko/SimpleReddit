package com.chumachenko.simpleReddit.data.api.response.newrespose

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AuthorFlairRichtext(
    val e: String?,
    val t: String?
): Parcelable