package com.emamagic.moviestreaming.ui.episode_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.db.entity.EpisodeEntity

class EpisodeListDiffCallback: DiffUtil.ItemCallback<EpisodeEntity>() {

    override fun areItemsTheSame(oldItem: EpisodeEntity, newItem: EpisodeEntity) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: EpisodeEntity, newItem: EpisodeEntity) = oldItem == newItem
}