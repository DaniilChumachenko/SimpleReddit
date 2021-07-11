package com.chumachenko.simpleReddit.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chumachenko.simpleReddit.data.repository.model.RedditItem
import com.chumachenko.simpleReddit.data.repository.RedditRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TopRedditViewModel @Inject constructor(private val redditRepository: RedditRepository) :
    ViewModel() {

    private val _redditResponseItem: MutableLiveData<ArrayList<RedditItem>> = MutableLiveData()
    val redditResponseItem: LiveData<ArrayList<RedditItem>> = _redditResponseItem

    private val _redditLocalItem: MutableLiveData<ArrayList<RedditItem>> = MutableLiveData()
    val redditLocalItem: LiveData<ArrayList<RedditItem>> = _redditLocalItem

    private val compositeDisposable = CompositeDisposable()

    fun getPostByType(subRed: String, typeSort: String, lastItem: String?) {
        compositeDisposable.add(
            redditRepository.getRedditPostsByType(subRed, typeSort, lastItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({
                    if (redditResponseItem.value?.size == null)
                        _redditResponseItem.value = it
                    else {
                        val oldlist = redditResponseItem.value
                        oldlist?.addAll(it)
                        _redditResponseItem.value = oldlist
                    }
                }), ({ error ->
                    error.printStackTrace()
                }))
        )
    }

    fun getPostByTypeAndTime(subRed: String, typeSort: String, timeSort: String) {
        compositeDisposable.add(
            redditRepository.getRedditPostsByTypeAndTime(subRed, typeSort, timeSort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({
                    _redditResponseItem.value = it
                }), ({ error ->
                    error.printStackTrace()
                }))
        )
    }

    fun getFromLocal() {
        compositeDisposable.add(
            redditRepository.getFromLocal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({
                    _redditLocalItem.value = it
                }), ({ error ->
                    error.printStackTrace()
                }))
        )
        if (redditLocalItem.value?.size == 0 || redditLocalItem.value == null)
            _redditLocalItem.value = arrayListOf()

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}