package com.chumachenko.simpleReddit.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chumachenko.simpleReddit.data.db.realmModel.RedditItemRealm
import com.chumachenko.simpleReddit.data.repository.RedditRepository
import com.chumachenko.simpleReddit.data.repository.model.RedditItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TopRedditViewModel @Inject constructor(private val redditRepository: RedditRepository) :
    ViewModel() {

    private val _redditResponseItem: MutableLiveData<Resource<ArrayList<RedditItem>>> =
        MutableLiveData()
    val redditResponseItem: LiveData<Resource<ArrayList<RedditItem>>> = _redditResponseItem

    private val _redditLocalItem: MutableLiveData<Resource<ArrayList<RedditItem>>> =
        MutableLiveData()
    val redditLocalItem: LiveData<Resource<ArrayList<RedditItem>>> = _redditLocalItem

    private val _redditItemForSort: MutableLiveData<Resource<ArrayList<RedditItem>>> =
        MutableLiveData()
    val redditItemForSort: LiveData<Resource<ArrayList<RedditItem>>> = _redditItemForSort

    private val compositeDisposable = CompositeDisposable()

    fun getPostByType(subRed: String, typeSort: String, lastItem: String?) {
        compositeDisposable.add(
            redditRepository.getRedditPostsByType(subRed, typeSort, lastItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({
                    if (redditResponseItem.value?.data?.size == null)
                        if (redditLocalItem.value?.data?.size ?: 0 > 0)
                            _redditResponseItem.value =
                                Resource.success(redditLocalItem.value?.data)
                        else
                            _redditResponseItem.value = Resource.success(it)
                    else {
                        val oldList = redditResponseItem.value?.data
                        oldList?.addAll(it)
                        _redditResponseItem.value = Resource.success(oldList)
                    }
                }), ({ error ->
                    _redditResponseItem.value = Resource.error()
                    error.printStackTrace()
                }))
        )
    }

    fun getFromLocal() {
        compositeDisposable.add(
            redditRepository.getFromLocal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    if (redditLocalItem.value == null)
                        _redditLocalItem.value = Resource.loading()
                }
                .subscribe(({
                    _redditLocalItem.value = Resource.success(it)
                }), ({ error ->
                    _redditResponseItem.value = Resource.error()
                    error.printStackTrace()
                }))

        )
    }

    fun getFromLocalForSort() {
        compositeDisposable.add(
            redditRepository.getFromLocal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    if (redditItemForSort.value == null)
                        _redditItemForSort.value = Resource.loading()
                }
                .subscribe(({
                    _redditItemForSort.value = Resource.success(it)
                }), ({ error ->
                    _redditItemForSort.value = Resource.error()
                    error.printStackTrace()
                }))

        )
    }

    fun clearStorage(data: ArrayList<RedditItem>?) {
        data?.sortBy { it.uts }
        val realmList = arrayListOf<RedditItemRealm>()
        data?.forEachIndexed { index, redditItem ->
            if (index > 49)
                realmList.add(redditItem.toRealm())
        }
        compositeDisposable.add(
            redditRepository.clearStorage(realmList)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    getFromLocal()
                }
                .subscribe(({
                }), ({ error ->
                    error.printStackTrace()
                }))
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}