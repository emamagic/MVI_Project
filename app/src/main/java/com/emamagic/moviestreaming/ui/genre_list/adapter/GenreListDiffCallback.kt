package com.emamagic.moviestreaming.ui.genre_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity

class GenreListDiffCallback: DiffUtil.ItemCallback<GenreEntity>() {

    override fun areItemsTheSame(oldItem: GenreEntity, newItem: GenreEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GenreEntity, newItem: GenreEntity): Boolean {
        return oldItem == newItem
    }


}