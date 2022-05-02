package com.sample.newsfeed.layers.presentation.ui.detail

import android.view.View
import com.sample.news.databinding.FragmentDetailViewBinding
import com.sample.newsfeed.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsItemDetailFragment :
    BaseFragment<FragmentDetailViewBinding>(FragmentDetailViewBinding::inflate) {

    // region LIFECYCLE

    override fun initUserInterface(view: View?) {

        activity?.intent?.let {
            viewDataBinding.apply {
                arguments?.let {
                    webView.loadUrl(NewsItemDetailFragmentArgs.fromBundle(it).url)
                }
            }
        }
    }

    // endregion


    // region COMPANION

    companion object {
        const val URL_KEY = "url"
    }

    // endregion
}
