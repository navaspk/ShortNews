package com.sample.newsfeed.layers.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.sample.news.databinding.ItemEachNewsBinding
import com.sample.newsfeed.base.BaseViewHolder
import com.sample.newsfeed.base.DiffCallback
import com.sample.newsfeed.layers.domain.model.DocsItem
import com.sample.newsfeed.layers.presentation.ui.event.ClickEvent

/**
 * Adapter class which create the view for the item
 */
class NewsListAdapter(
    private val event: (ClickEvent) -> Unit
) : PagedListAdapter<DocsItem, BaseViewHolder<DocsItem>>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DocsItem> {
        return NewsListViewHolder(
            ItemEachNewsBinding.inflate(LayoutInflater.from(parent.context)), event
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<DocsItem>, position: Int) {
        val newsItem = getItem(position)
        holder.apply {
            holder.bindView(getItem(position)!!)
            itemView.tag = newsItem
        }
    }

}
