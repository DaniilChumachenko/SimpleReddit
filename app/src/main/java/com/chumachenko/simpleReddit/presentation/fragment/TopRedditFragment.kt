package com.chumachenko.simpleReddit.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chumachenko.simpleReddit.presentation.adapter.TopRedditAdapter
import com.chumachenko.simpleReddit.R
import com.chumachenko.simpleReddit.presentation.viewmodel.TopRedditViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_top_reddit.*
import javax.inject.Inject


class TopRedditFragment : Fragment(R.layout.fragment_top_reddit) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: TopRedditViewModel

    private var topRedditAdapter: TopRedditAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onViewCreated(view, savedInstanceState)
        button_first.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        bindViewModel()
        initObservers()
    }

    private fun initObservers() = viewModel.apply {
        getPostByType("popular","top")
        viewModel.redditItem.observe(viewLifecycleOwner){
            initAdapter()
        }
    }

    private fun initAdapter(): RecyclerView = rvTopReddit.apply {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        topRedditAdapter = TopRedditAdapter(viewModel.redditItem.value?: arrayListOf())
        adapter = topRedditAdapter
        itemAnimator = null
    }
    private fun bindViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TopRedditViewModel::class.java)
    }
}