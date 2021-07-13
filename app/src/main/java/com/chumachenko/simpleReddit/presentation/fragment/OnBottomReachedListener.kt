package com.chumachenko.simpleReddit.presentation.fragment

import com.chumachenko.simpleReddit.data.repository.model.RedditItem

interface OnBottomReachedListener {
    fun onBottomReached(item: RedditItem, position: Int)
    fun lastItem(last: Boolean)
}