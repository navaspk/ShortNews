package com.sample.newsfeed.layers.presentation.ui.home.frag

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.core.extensions.safeGet
import com.sample.news.R
import com.sample.news.databinding.FragmentNewsHomeBinding
import com.sample.newsfeed.base.BaseFragment
import com.sample.newsfeed.layers.domain.api.NetworkState
import com.sample.newsfeed.layers.presentation.ui.event.ClickEvent
import com.sample.newsfeed.layers.presentation.ui.home.adapter.NewsListAdapter
import com.sample.newsfeed.layers.presentation.ui.home.viewmodel.NewsListViewModel
import com.sample.newsfeed.layers.presentation.ui.detail.NewsItemDetailFragment.Companion.URL_KEY
import com.sample.newsfeed.layers.presentation.ui.home.adapter.ItemOffsetDecoration
import com.sample.newsfeed.utils.hasInternetConnection
import com.sample.newsfeed.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * The fragment is used for display the home screen content, ie list of News
 */
@AndroidEntryPoint
class NewsListFragment :
    BaseFragment<FragmentNewsHomeBinding>(FragmentNewsHomeBinding::inflate) {

    // region VARIABLE

    private var listAdapter: NewsListAdapter? = null
    private val listViewModel: NewsListViewModel by viewModels()

    private var event: (ClickEvent) -> Unit = { event ->
        when (event) {
            is ClickEvent.ItemClicked -> {
                onItemClick(event.pos)
            }
        }
    }

    // endregion

    // region LIFECYCLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel.let {
            lifecycleScope.launch {
                it.userIntent.send(NewsListViewModel.NewsIntent.GetSearchedNews)
            }
        }
    }

    override fun initUserInterface(view: View?) {
        initRecyclerView()
        initObserverForStates()
    }

    // endregion


    // region UTILS

    private fun initRecyclerView() {
        listAdapter = NewsListAdapter(event)
        viewDataBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
            addItemDecoration(ItemOffsetDecoration(requireContext(), R.dimen.dimen_4_dp))
        }
    }

    private fun initObserverForStates() {

        val data = listViewModel.searchedNewsList

        data?.networkState?.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkState.LOADING -> {
                    viewDataBinding.progressBar.visibility = View.VISIBLE
                }
                is NetworkState.ERROR -> {
                    viewDataBinding.progressBar.visibility = View.GONE
                    // Handling fail state
                }
                is NetworkState.LOADED -> {
                    viewDataBinding.progressBar.visibility = View.GONE
                }
            }
        }
        data?.pagedList?.observe(this) {
            listAdapter?.submitList(it)
        }

    }

    // endregion


    // region UTIL

    private fun onItemClick(position: Int) {
        if (activity?.hasInternetConnection() == true) {
            findNavController().navigate(
                R.id.move_to_details,
                bundleOf(URL_KEY to listAdapter?.currentList?.get(position)?.webUrl.safeGet())
            )
        } else {
            viewDataBinding.root.showSnackBar(getString(R.string.error_internet_not_available))
        }
    }

    // endregion

}
