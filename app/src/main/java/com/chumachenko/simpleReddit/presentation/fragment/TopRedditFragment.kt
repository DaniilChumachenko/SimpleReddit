package com.chumachenko.simpleReddit.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chumachenko.simpleReddit.GlobalConstants
import com.chumachenko.simpleReddit.R
import com.chumachenko.simpleReddit.data.repository.model.RedditItem
import com.chumachenko.simpleReddit.presentation.adapter.TopRedditAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initAdapter()
        initObservers()
    }

    private fun initObservers() = viewModel.apply {
        getFromLocal()
        redditLocalItem.observe(viewLifecycleOwner) { local ->
            when (local.status) {
                Status.LOADING -> {
                    pgLoadReddit.visibility = VISIBLE
                    if (local.data?.size == null || local.data?.size == 0)
                        getPostByType("popular", "top", null)
                }
                Status.SUCCESS -> {
                    pgLoadReddit.visibility = GONE
                    local.data?.let {
                        when {
                            it.size in 1..49 -> {
                                redditAdapter?.updateList(it)
                            }
                            it.size > 50 -> {
                                clearStorage(it)
                            }
                            else -> {
                                redditAdapter?.updateList(it)
                                Snackbar.make(
                                    rvTopReddit,
                                    "It be top 50 of reddit!",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    pgLoadReddit.visibility = GONE
                    local.throwable?.let { error ->
                        error.printStackTrace()
                        Snackbar.make(rvTopReddit, "Loading error!", Snackbar.LENGTH_SHORT).show()
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
                    if (response.data?.size ?: 0 in 1..49)
                        response.data?.let { redditAdapter?.updateList(it) }
                    else
                        Snackbar.make(rvTopReddit, "It be top 50 of reddit!", Snackbar.LENGTH_LONG)
                            .show()
                }
                Status.ERROR -> {
                    pgLoadReddit.visibility = GONE
                    response.throwable?.let { error ->
                        error.printStackTrace()
                        Snackbar.make(rvTopReddit, "Loading error!", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
            pgLoadReddit.visibility = GONE
        }
    }

    private fun initAdapter(): RecyclerView = rvTopReddit.apply {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        redditAdapter = TopRedditAdapter(arrayListOf(), object : OnBottomReachedListener {
            override fun onBottomReached(item: RedditItem) {
                pgLoadReddit.visibility = VISIBLE
                viewModel.getPostByType(
                    "popular",
                    "top",
                    item.after
                )
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