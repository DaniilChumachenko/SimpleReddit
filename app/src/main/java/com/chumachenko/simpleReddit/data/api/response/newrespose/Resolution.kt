package com.chumachenko.simpleReddit.data.api.response.newrespose

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Resolution(
    val height: Int?,
    val url: String?,
    val width: Int?
): Parcelable