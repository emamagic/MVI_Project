package com.emamagic.moviestreaming.ui.modules.movie.adaper

import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.data.db.entity.SeasonEntity

class SeasonListDiffCallback: DiffUtil.ItemCallback<SeasonEntity>() {

    override fun areItemsTheSame(oldItem: SeasonEntity, newItem: SeasonEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SeasonEntity, newItem: SeasonEntity): Boolean {
        return oldItem == newItem
    }


}