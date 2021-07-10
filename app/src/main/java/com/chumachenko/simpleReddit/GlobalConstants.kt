package com.chumachenko.simpleReddit

import java.util.*

object GlobalConstants {
    const val IMAGE_REASON_SHOWING_INVITE_MAP_IMAGE = "showing_invite_map_image"
    const val LOGIN_STATE_RESULT = "LOGIN_STATE_RESULT"
    const val LOGIN_RESULT = "LOGIN_STATE_RESULT"
    const val IS_FIRST_SESSION = "IS_FIRST_SESSION"
    const val BASE_ENDPOINT = "https://www.reddit.com/"
    const val AUTHORIZATION_KEY = "Authorization"
    const val AUTHORIZATION_BASE = "bearer "
    const val USER_AGENT_KEY = "User-Agent"
    const val USER_AGENT = "android:ml.docilealligator.infinityforreddit:v4.1.1 (by /u/Hostilenemy)"

     const val AUTH_URL = "https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
            "&response_type=code&state=%s&redirect_uri=%s&" +
            "duration=permanent&scope=identity"

     const val CLIENT_ID = "ABCDEFGHIJKLM012345-AA"

     const val REDIRECT_URI = "http://www.example.com/my_redirect"

     const val STATE = "MY_RANDOM_STRING_1"

     const val ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token"

    fun getOAuthHeader(accessToken: String): Map<String, String>? {
        val params: MutableMap<String, String> = HashMap()
        params[AUTHORIZATION_KEY] =
            AUTHORIZATION_BASE + accessToken
        params[USER_AGENT_KEY] = USER_AGENT
        return params
    }
}

