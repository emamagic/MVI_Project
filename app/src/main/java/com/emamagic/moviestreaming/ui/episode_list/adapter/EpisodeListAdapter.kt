package com.emamagic.moviestreaming.ui.episode_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emamagic.moviestreaming.databinding.ItemEpisodeBinding
import com.emamagic.moviestreaming.db.entity.EpisodeEntity
import com.squareup.picasso.Picasso

class EpisodeListAdapter: ListAdapter<EpisodeEntity ,EpisodeListAdapter.EpisodeListViewHolder>(EpisodeListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeListViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return EpisodeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeListViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class EpisodeListViewHolder constructor(
        val binding: ItemEpisodeBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(item: EpisodeEntity) {
            binding.apply {
                time.text = item.time
                name.text = item.name
                detail.text = item.detail
                Picasso.get().load(item.imageLink).resize(500,500).into(imgEpisode)
            }
        }
    }

}