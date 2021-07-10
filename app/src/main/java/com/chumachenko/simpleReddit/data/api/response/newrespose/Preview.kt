package com.chumachenko.simpleReddit.data.api.response.newrespose

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Preview(
    val enabled: Boolean?,
    val images: List<Image>?,
    val reddit_video_preview: RedditVideoPreview?
): Parcelable