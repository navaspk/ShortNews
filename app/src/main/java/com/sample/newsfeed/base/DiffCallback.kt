package com.sample.newsfeed.base

import androidx.recyclerview.widget.DiffUtil
import com.sample.newsfeed.layers.domain.model.DocsItem

class DiffCallback : DiffUtil.ItemCallback<DocsItem>() {

    override fun areItemsTheSame(oldItem: DocsItem, newItem: DocsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DocsItem, newItem: DocsItem): Boolean {
        return oldItem == newItem
    }
}
