package com.emamagic.moviestreaming.ui.search

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.mapper.MovieMapper
import com.emamagic.moviestreaming.network.dto.MovieDto
import com.emamagic.moviestreaming.repository.search.SearchRepository
import com.emamagic.moviestreaming.ui.search.contract.CurrentSearchState
import com.emamagic.moviestreaming.ui.search.contract.SearchEffect
import com.emamagic.moviestreaming.ui.search.contract.SearchEvent
import com.emamagic.moviestreaming.ui.search.contract.SearchState
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val movieMapper: MovieMapper
): BaseViewModel<SearchState ,SearchEffect ,SearchEvent>() {

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
            is ResultWrapper.Success -> setState { copy(movies = response.data!! ,currentState = CurrentSearchState.MOVIES_RECEIVED) }
            is ResultWrapper.Failed -> setEffect { SearchEffect.ShowToast("${response.error?.message}" ,ToastyMode.MODE_TOAST_ERROR) }
            is ResultWrapper.FetchLoading -> { /* Do Nothing */ }
        }.exhaustive
    }

    private fun movieClicked(movie: MovieDto) = viewModelScope.launch {
        setEffect { SearchEffect.Navigate(SearchFragmentDirections.actionSearchFragmentToMovieFragment(movieMapper.mapFromEntity(movie))) }
    }

}