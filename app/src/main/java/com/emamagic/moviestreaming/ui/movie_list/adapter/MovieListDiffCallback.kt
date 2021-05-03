package com.emamagic.moviestreaming.ui.movie_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.MovieWithFavorite

class MovieListDiffCallback: DiffUtil.ItemCallback<MovieWithFavorite>() {

    override fun areItemsTheSame(oldItem: MovieWithFavorite, newItem: MovieWithFavorite): Boolean {
        return oldItem.movie.id == newItem.movie.id
    }

    override fun areContentsTheSame(oldItem: MovieWithFavorite, newItem: MovieWithFavorite): Boolean {
        return oldItem == newItem
    }


}