package com.emamagic.moviestreaming.ui.movie.adaper

import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.db.entity.CastEntity
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SeasonEntity

class SeasonListDiffCallback: DiffUtil.ItemCallback<SeasonEntity>() {

    override fun areItemsTheSame(oldItem: SeasonEntity, newItem: SeasonEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SeasonEntity, newItem: SeasonEntity): Boolean {
        return oldItem == newItem
    }


}