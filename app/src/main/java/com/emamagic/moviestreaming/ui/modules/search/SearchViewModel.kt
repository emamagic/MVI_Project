package com.emamagic.moviestreaming.ui.modules.search

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.data.network.dto.MovieDto
import com.emamagic.moviestreaming.data.repository.search.SearchRepository
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.provider.mapper.MovieMapper
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import com.emamagic.moviestreaming.ui.base.BaseViewModel
import com.emamagic.moviestreaming.ui.modules.search.contract.SearchEvent
import com.emamagic.moviestreaming.ui.modules.search.contract.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val movieMapper: MovieMapper
): BaseViewModel<SearchState, CommonEffect, SearchEvent>() {

    override fun createInitialState() = SearchState.initialize()

    override fun handleEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.SearchMovies -> searchMovies(event.category ,event.query)
            is SearchEvent.MovieClicked -> movieClicked(event.movie)
        }.exhaustive
    }

    private fun searchMovies(category: String, query: String) = viewModelScope.launch {
        val response = repository.searchMovies(category ,query)
        when(response){
            is ResultWrapper.Success -> setState { copy(movies = response.data) }
            is ResultWrapper.Failed -> setEffect { CommonEffect.ShowToast("${response.error?.message}" ,ToastyMode.MODE_TOAST_ERROR) }
            is ResultWrapper.FetchLoading -> { /* Do Nothing */ }
        }.exhaustive
    }

    private fun movieClicked(movie: MovieDto) = viewModelScope.launch {
        setEffect { CommonEffect.Navigate(SearchFragmentDirections.actionSearchFragmentToMovieFragment(movieMapper.mapFromEntity(movie))) }
    }

}