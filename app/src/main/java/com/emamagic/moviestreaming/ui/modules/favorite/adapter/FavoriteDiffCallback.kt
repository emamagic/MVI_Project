package com.emamagic.moviestreaming.ui.modules.favorite.adapter

import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.data.db.entity.MovieEntity

class FavoriteDiffCallback: DiffUtil.ItemCallback<MovieEntity>() {

    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem == newItem
}