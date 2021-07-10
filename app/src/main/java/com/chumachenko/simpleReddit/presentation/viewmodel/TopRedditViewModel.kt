package com.chumachenko.simpleReddit.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chumachenko.simpleReddit.data.api.response.RedditItem
import com.chumachenko.simpleReddit.data.repository.RedditRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TopRedditViewModel @Inject constructor(private val redditRepository: RedditRepository) : ViewModel() {

    private val _redditItem: MutableLiveData<ArrayList<RedditItem>> = MutableLiveData()
    val redditItem: LiveData<ArrayList<RedditItem>> = _redditItem

    private val compositeDisposable = CompositeDisposable()

    fun getPostFromApi() {
        compositeDisposable.add(
            redditRepository.getFeedPosts("popular", "top")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({
                    _redditItem.value = it.toFeedList()
                }), ({ error ->
                    error.printStackTrace()
                }))
        )
    }
}