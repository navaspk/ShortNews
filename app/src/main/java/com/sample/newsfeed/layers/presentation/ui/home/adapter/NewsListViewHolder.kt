package com.sample.newsfeed.layers.presentation.ui.home.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sample.core.extensions.safeGet
import com.sample.news.R
import com.sample.newsfeed.base.BaseViewHolder
import com.sample.news.databinding.ItemEachNewsBinding
import com.sample.newsfeed.layers.domain.model.DocsItem
import com.sample.newsfeed.layers.presentation.ui.event.ClickEvent

/**
 * View holder class bind the data to the view
 */
class NewsListViewHolder(
    private val recyclerBinding: ItemEachNewsBinding,
    event: (ClickEvent) -> Unit
) : BaseViewHolder<DocsItem>(event, recyclerBinding.root) {

    // region VARIABLE

    private val domain = "https://www.nytimes.com/"

    // endregion


    // region OVERRIDDEN

    override fun bindView(item: DocsItem) {
        loadNewsImage(item)
        showHeadingSnippetAndDate(item)
    }

    // endregion


    // region UTIL

    private fun loadNewsImage(item: DocsItem) {
        item.multimedia?.let { media ->
            if (media.isNotEmpty()) {
                Glide.with(recyclerBinding.newsImages.context)
                    .load("$domain${media[0]?.url}")
                    .centerCrop()
                    .error(R.drawable.background_gradient)
                    .placeholder(R.drawable.background_gradient)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(recyclerBinding.newsImages)
            } else {
                setDefaultImage()
            }
        } ?: setDefaultImage()

    }

    private fun setDefaultImage() {
        recyclerBinding.newsImages.setImageResource(R.drawable.background_gradient)
    }

    private fun showHeadingSnippetAndDate(item: DocsItem) {
        recyclerBinding.apply {
            headingTextView.text = item.headline?.main.safeGet()
            newsSnippetTextView.text = item.snippet?.ifEmpty { item.leadParagraph }
            dateTextView.text = item.pubDate?.substring(0, 10)
        }
    }

    // endregion

}
