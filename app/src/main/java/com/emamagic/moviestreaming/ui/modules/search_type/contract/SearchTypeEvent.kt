package com.emamagic.moviestreaming.ui.modules.search_type.contract

import com.emamagic.moviestreaming.ui.base.BaseEvent

sealed class SearchTypeEvent: BaseEvent {
    data class SearchTypeClicked(@SearchType val type: String): SearchTypeEvent()
}