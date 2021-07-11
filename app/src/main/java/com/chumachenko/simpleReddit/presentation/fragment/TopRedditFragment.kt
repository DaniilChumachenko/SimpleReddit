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
import com.chumachenko.simpleReddit.presentation.adapter.TopRedditAdapter
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
        getPostByType("popular", "top", null)
        redditResponseItem.observe(viewLifecycleOwner) { list ->
            if (list.size >= 50)
                Snackbar.make(rvTopReddit, "It be top 50 of reddit!", Snackbar.LENGTH_LONG).show()
            else
                redditAdapter?.updateList(list)
            pgLoadReddit.visibility = GONE
        }
    }

    private fun initAdapter(): RecyclerView = rvTopReddit.apply {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        redditAdapter = TopRedditAdapter(arrayListOf(), object : OnBottomReachedListener {
            override fun onBottomReached(position: Int) {
                pgLoadReddit.visibility = VISIBLE
                viewModel.getPostByType(
                    "popular",
                    "top",
                    viewModel.redditResponseItem.value?.last()?.after
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