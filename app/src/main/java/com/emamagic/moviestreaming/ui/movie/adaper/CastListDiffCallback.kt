package com.emamagic.moviestreaming.ui.movie.adaper

import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.db.entity.CastEntity
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity

class CastListDiffCallback: DiffUtil.ItemCallback<CastEntity>() {

    override fun areItemsTheSame(oldItem: CastEntity, newItem: CastEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CastEntity, newItem: CastEntity): Boolean {
        return oldItem == newItem
    }


}