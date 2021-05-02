package com.emamagic.moviestreaming.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentSearchBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.network.dto.MovieDto
import com.emamagic.moviestreaming.ui.search.adapter.SearchAdapter
import com.emamagic.moviestreaming.ui.search.contract.CurrentSearchState
import com.emamagic.moviestreaming.ui.search.contract.SearchEffect
import com.emamagic.moviestreaming.ui.search.contract.SearchEvent
import com.emamagic.moviestreaming.ui.search.contract.SearchState
import com.emamagic.moviestreaming.ui.search_type.contract.SearchTypeEffect
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.onQueryTextListener
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: BaseFragment<FragmentSearchBinding ,SearchState ,SearchEffect ,SearchEvent ,SearchViewModel>() ,
        SearchAdapter.Interaction{

    override val viewModel: SearchViewModel by viewModels()
    private val args: SearchFragmentArgs by navArgs()
    private lateinit var searchAdapter: SearchAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchAdapter = SearchAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBack.setOnClickListener { findNavController().popBackStack() }
        binding.title.text = args.searchType
        binding.edtSearch.onQueryTextListener {
            if (it.isNotEmpty()) viewModel.setEvent(SearchEvent.SearchMovies(args.searchType ,it))
        }

    }

    override fun renderViewState(viewState: SearchState) {
        when(viewState.currentState){
            CurrentSearchState.NON_STATE -> { /* Do Nothing */ }
            CurrentSearchState.MOVIES_RECEIVED -> setUpSearchRecycler(viewState.movies.movies)
        }
    }

    override fun renderViewEffect(viewEffect: SearchEffect) {
        when(viewEffect) {
            is SearchEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
            is SearchEffect.Navigate -> findNavController().navigate(viewEffect.navDirections)
            is SearchEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
        }.exhaustive
    }

    private fun setUpSearchRecycler(list: List<MovieDto>) {
        binding.recyclerViewSearch.adapter = searchAdapter
        binding.recyclerViewSearch.setHasFixedSize(true)
        binding.recyclerViewSearch.itemAnimator = null
        searchAdapter.submitList(list)
    }

    override fun onSearchItemClicked(item: MovieDto) {
        viewModel.setEvent(SearchEvent.MovieClicked(item))
    }
}