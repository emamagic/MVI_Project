package com.emamagic.moviestreaming.ui.search_type

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.ui.search_type.contract.SearchType
import com.emamagic.moviestreaming.ui.search_type.contract.SearchTypeEffect
import com.emamagic.moviestreaming.ui.search_type.contract.SearchTypeEvent
import com.emamagic.moviestreaming.ui.search_type.contract.SearchTypeState
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchTypeViewModel @Inject constructor(): BaseViewModel<SearchTypeState ,SearchTypeEffect , SearchTypeEvent>() {

    override fun createInitialState() = SearchTypeState()

    override fun handleEvent(event: SearchTypeEvent) {
        when(event){
            is SearchTypeEvent.SearchTypeClicked -> searchTypeClicked(event.type)
        }.exhaustive
    }

    private fun searchTypeClicked(@SearchType type: String) = viewModelScope.launch {
        setEffect { SearchTypeEffect.Navigate(SearchTypeFragmentDirections.actionSearchTypeFragmentToSearchFragment(type)) }
    }
}