package com.emamagic.moviestreaming.ui.genre_type.adapter

import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.db.entity.GenreEntity

class GenreTypeDiffCallback: DiffUtil.ItemCallback<GenreEntity>() {

    override fun areItemsTheSame(oldItem: GenreEntity, newItem: GenreEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GenreEntity, newItem: GenreEntity): Boolean {
        return oldItem == newItem
    }


}