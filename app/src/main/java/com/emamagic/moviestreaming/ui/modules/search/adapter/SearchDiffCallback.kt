package com.emamagic.moviestreaming.ui.modules.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.data.network.dto.MovieDto

class SearchDiffCallback: DiffUtil.ItemCallback<MovieDto>() {

    override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto) = oldItem == newItem
}