package com.emamagic.moviestreaming.ui.search_type.contract

import com.emamagic.moviestreaming.base.BaseEvent

sealed class SearchTypeEvent: BaseEvent {
    data class SearchTypeClicked(@SearchType val type: String): SearchTypeEvent()
}