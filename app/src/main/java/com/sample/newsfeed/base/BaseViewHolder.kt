package com.sample.newsfeed.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sample.newsfeed.layers.presentation.ui.event.ClickEvent
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<T>(private val event: (ClickEvent) -> Unit, bindingView: View) :
    RecyclerView.ViewHolder(bindingView), View.OnClickListener, LayoutContainer {

    override val containerView = itemView

    init {
        itemView.setOnClickListener(this)
    }

    abstract fun bindView(item: T)

    override fun onClick(p0: View?) {
        event.invoke(ClickEvent.ItemClicked(adapterPosition))
    }
}
