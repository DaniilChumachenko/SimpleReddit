package com.chumachenko.simpleReddit.presentation.support

import androidx.recyclerview.widget.DiffUtil
import com.chumachenko.simpleReddit.data.repository.model.RedditItem


class DiffUtilCallback(
    private val oldList: List<RedditItem>,
    private val newList: List<RedditItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.id == new.id
    }
}