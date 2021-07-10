package com.chumachenko.simpleReddit.data.api.response.newrespose

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Image(
    val id: String?,
    val resolutions: List<Resolution>?,
    val source: Source?
): Parcelable