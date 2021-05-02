package com.emamagic.moviestreaming.ui.genre_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.db.entity.MovieEntity

class GenreListDiffCallback: DiffUtil.ItemCallback<MovieEntity>() {

    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem == newItem
    }


}