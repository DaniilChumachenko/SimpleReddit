package com.chumachenko.simpleReddit.data.api.response.newrespose

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class AllAwarding(
    @SerializedName("award_sub_type") val award_sub_type: String?,
    @SerializedName("award_type") val award_type: String?,
    @SerializedName("awardings_required_to_grant_benefits") val awardings_required_to_grant_benefits: String?,
    @SerializedName("coin_price") val coin_price: Int?,
    @SerializedName("coin_reward") val coin_reward: Int?,
    @SerializedName("count") val count: Int?,
    @SerializedName("days_of_drip_extension") val days_of_drip_extension: Int?,
    @SerializedName("days_of_premium") val days_of_premium: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("end_date") val end_date: String?,
    @SerializedName("giver_coin_reward") val giver_coin_reward: Int?,
    @SerializedName("icon_format") val icon_format: String?,
    @SerializedName("icon_height") val icon_height: Int?,
    @SerializedName("icon_url") val icon_url: String?,
    @SerializedName("icon_width") val icon_width: Int?,
    @SerializedName("id") val id: String?,
    @SerializedName("is_enabled") val is_enabled: Boolean?,
    @SerializedName("is_new") val is_new: Boolean?,
    @SerializedName("name") val name: String?,
    @SerializedName("penny_donate") val penny_donate: Int?,
    @SerializedName("penny_price") val penny_price: Int?,
    @SerializedName("resized_icons") val resized_icons: List<ResizedIcon>?,
    @SerializedName("resized_static_icons") val resized_static_icons: List<ResizedStaticIcon>?,
    @SerializedName("start_date") val start_date: String?,
    @SerializedName("static_icon_height") val static_icon_height: Int?,
    @SerializedName("static_icon_url") val static_icon_url: String?,
    @SerializedName("static_icon_width") val static_icon_width: Int?,
    @SerializedName("subreddit_coin_reward") val subreddit_coin_reward: Int?,
    @SerializedName("subreddit_id") val subreddit_id: String?
): Parcelable