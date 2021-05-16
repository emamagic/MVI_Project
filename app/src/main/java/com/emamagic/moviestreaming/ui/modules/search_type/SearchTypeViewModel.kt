package com.emamagic.moviestreaming.ui.modules.search_type

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.ui.base.BaseViewModel
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.ui.modules.search_type.contract.SearchType
import com.emamagic.moviestreaming.ui.modules.search_type.contract.SearchTypeEvent
import com.emamagic.moviestreaming.ui.modules.search_type.contract.SearchTypeState
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchTypeViewModel @Inject constructor(): BaseViewModel<SearchTypeState, CommonEffect, SearchTypeEvent>() {

    override fun createInitialState() = SearchTypeState()

    override fun handleEvent(event: SearchTypeEvent) {
        when(event){
            is SearchTypeEvent.SearchTypeClicked -> searchTypeClicked(event.type)
        }.exhaustive
    }

    override fun showError(errorMessage: String) {
        CommonEffect.ShowToast(errorMessage, ToastyMode.MODE_TOAST_ERROR)
    }

    private fun searchTypeClicked(@SearchType type: String) = viewModelScope.launch {
        setEffect { CommonEffect.Navigate(SearchTypeFragmentDirections.actionSearchTypeFragmentToSearchFragment(type)) }
    }

}