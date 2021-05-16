package com.emamagic.moviestreaming.ui.modules.movie.adaper

import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.data.db.entity.CastEntity

class CastListDiffCallback: DiffUtil.ItemCallback<CastEntity>() {

    override fun areItemsTheSame(oldItem: CastEntity, newItem: CastEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CastEntity, newItem: CastEntity): Boolean {
        return oldItem == newItem
    }


}