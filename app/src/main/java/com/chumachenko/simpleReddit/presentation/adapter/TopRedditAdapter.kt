package com.chumachenko.simpleReddit.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chumachenko.simpleReddit.GlobalConstants.MAX_POSTS_COUNT
import com.chumachenko.simpleReddit.R
import com.chumachenko.simpleReddit.data.repository.model.RedditItem
import com.chumachenko.simpleReddit.presentation.fragment.OnBottomReachedListener
import com.chumachenko.simpleReddit.presentation.fragment.OnOpenPostListener
import com.chumachenko.simpleReddit.presentation.support.DiffUtilCallback
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_reddit.view.*
import java.util.*


class TopRedditAdapter(
    private var list: ArrayList<RedditItem>,
    private var onBottomReachedListener: OnBottomReachedListener,
    private var onOpenPostListener: OnOpenPostListener
) : RecyclerView.Adapter<TopRedditAdapter.ItemTagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTagViewHolder =
        ItemTagViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_reddit, parent, false)
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemTagViewHolder, position: Int) {
        holder.bind(list[position])
        if (position == list.size - 1 && list.size < MAX_POSTS_COUNT)
            onBottomReachedListener.onBottomReached(list[position], position)
        else if (position == MAX_POSTS_COUNT - 1)
            onBottomReachedListener.lastItem(true)
    }

    fun updateList(newList: ArrayList<RedditItem>) = DiffUtil.calculateDiff(DiffUtilCallback(this.list, newList)).let {
        list.clear()
        list.addAll(newList)
        it.dispatchUpdatesTo(this)
    }

    inner class ItemTagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                onOpenPostListener.openPost(list[adapterPosition].permalink)
            }
        }

        fun bind(redditItem: RedditItem) = itemView.apply {
            tvTitleReddit.text = redditItem.title
            tvSubredditName.text = redditItem.subreddit
            tvNumComments.text = redditItem.num_comments.toString()
            tvScore.text = redditItem.score.toString()
            redditItem.uts?.let {
                tvTimePub.text = TimeAgo.using(it.toLong() * 1000)
            }
            if (redditItem.thumbnail?.length ?: 0 < 10) {
                ivListImage.visibility = GONE
            } else {
                ivListImage.visibility = VISIBLE
                Picasso.get().load(redditItem.thumbnail).into(ivListImage)
            }
        }

    }
}