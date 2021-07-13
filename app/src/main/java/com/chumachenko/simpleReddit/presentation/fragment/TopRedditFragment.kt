package com.chumachenko.simpleReddit.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chumachenko.simpleReddit.GlobalConstants
import com.chumachenko.simpleReddit.GlobalConstants.MAX_POSTS_COUNT
import com.chumachenko.simpleReddit.R
import com.chumachenko.simpleReddit.data.repository.model.RedditItem
import com.chumachenko.simpleReddit.presentation.adapter.TopRedditAdapter
import com.chumachenko.simpleReddit.presentation.viewmodel.SortType
import com.chumachenko.simpleReddit.presentation.viewmodel.Status
import com.chumachenko.simpleReddit.presentation.viewmodel.TopRedditViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_top_reddit.*
import javax.inject.Inject


class TopRedditFragment : Fragment(R.layout.fragment_top_reddit) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: TopRedditViewModel

    private var redditAdapter: TopRedditAdapter? = null
    private var lastPosition = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initAdapter()
        initObservers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    private fun initObservers() = viewModel.apply {
        getFromLocal()
        redditLocalItem.observe(viewLifecycleOwner) { local ->
            when (local.status) {
                Status.LOADING -> {
                    pgLoadReddit.visibility = VISIBLE
                    if (local.data?.size == null || local.data?.size == 0)
                        getPostByType(
                            getString(R.string.main_subreddit),
                            getString(R.string.main_filter_top),
                            null
                        )
                }
                Status.SUCCESS -> {
                    pgLoadReddit.visibility = GONE
                    local.data?.let { list ->
                        list.sortByDescending { it.score }
                        when {
                            list.size in 1 until MAX_POSTS_COUNT -> redditAdapter?.updateList(list)
                            list.size > MAX_POSTS_COUNT -> clearStorage(list)
                            else -> redditAdapter?.updateList(list)
                        }
                    }
                }
                Status.ERROR -> {
                    pgLoadReddit.visibility = GONE
                    local.throwable?.let { error ->
                        error.printStackTrace()
                        Snackbar.make(
                            rvTopReddit,
                            getString(R.string.loading_error),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        redditResponseItem.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.LOADING -> {
                    pgLoadReddit.visibility = VISIBLE
                }
                Status.SUCCESS -> {
                    pgLoadReddit.visibility = GONE
                    response.data?.let {
                        if (it.size in 1..MAX_POSTS_COUNT)
                            redditAdapter?.updateList(it)
                        rvTopReddit.scrollToPosition(lastPosition)
                    }
                }
                Status.ERROR -> {
                    pgLoadReddit.visibility = GONE
                    response.throwable?.let { error ->
                        error.printStackTrace()
                        Snackbar.make(
                            rvTopReddit,
                            getString(R.string.loading_error),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            pgLoadReddit.visibility = GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_by_rating -> {
                sortedByType(SortType.RATING)
                true
            }
            R.id.filter_by_comments -> {
                sortedByType(SortType.COMMENT)
                true
            }
            else -> false
        }
    }

    private fun sortedByType(sortType: SortType) {
        viewModel.getFromLocalForSort()
        viewModel.redditItemForSort.observe(viewLifecycleOwner) { local ->
            when (local.status) {
                Status.LOADING -> {
                    pgLoadReddit.visibility = VISIBLE
                }
                Status.SUCCESS -> {
                    pgLoadReddit.visibility = GONE
                    when (sortType) {
                        SortType.RATING -> local.data?.sortByDescending { it.score }
                        SortType.COMMENT -> local.data?.sortByDescending { it.num_comments }
                    }
                    redditAdapter?.updateList(local.data ?: arrayListOf())
                }
                Status.ERROR -> {
                    pgLoadReddit.visibility = GONE
                    local.throwable?.let { error ->
                        error.printStackTrace()
                        Snackbar.make(
                            rvTopReddit,
                            getString(R.string.loading_error),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun initAdapter(): RecyclerView = rvTopReddit.apply {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        redditAdapter = TopRedditAdapter(arrayListOf(), object : OnBottomReachedListener {
            override fun onBottomReached(item: RedditItem, position: Int) {
                lastPosition = position
                pgLoadReddit.visibility = VISIBLE
                viewModel.getPostByType(
                    getString(R.string.main_subreddit),
                    getString(R.string.main_filter_top),
                    item.after
                )
            }

            override fun lastItem(last: Boolean) {
                if (last)
                    Snackbar.make(
                        rvTopReddit,
                        getString(R.string.last_post),
                        Snackbar.LENGTH_LONG
                    ).show()
            }
        }, object : OnOpenPostListener {
            override fun openPost(permalink: String?) {
                val uri = Uri.parse(GlobalConstants.BASE_ENDPOINT + permalink)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        })
        adapter = redditAdapter
        itemAnimator = null
    }

    private fun bindViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TopRedditViewModel::class.java)
    }
}