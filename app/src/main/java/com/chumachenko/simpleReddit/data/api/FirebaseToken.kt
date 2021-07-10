package com.chumachenko.simpleReddit.data.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FirebaseToken(
        @SerializedName("push_token")
        @Expose
        private var token: String? = null
)