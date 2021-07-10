package com.chumachenko.simpleReddit.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chumachenko.simpleReddit.R
import com.chumachenko.simpleReddit.data.api.response.RedditItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.v3_item_reddit.view.*

class TopRedditAdapter(private var list: ArrayList<RedditItem>) :
        RecyclerView.Adapter<TopRedditAdapter.ItemTagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTagViewHolder =
            ItemTagViewHolder(
                    LayoutInflater
                            .from(parent.context)
                            .inflate(R.layout.v3_item_reddit, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemTagViewHolder, position: Int) {
        holder.bind(list[position])
    }
    fun updateList( list: ArrayList<RedditItem>){
        this.list = list
        notifyDataSetChanged()
    }

    inner class ItemTagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
//                param.listIsSelect(list[bindingAdapterPosition])
            }
        }

        fun bind(redditItem: RedditItem) = itemView.apply {
            tvListName.text = redditItem.title
            Picasso.get().load(redditItem.tubnail).into(ivListImage);
//            tvListName.text = saveList.listName
        }

    }
}