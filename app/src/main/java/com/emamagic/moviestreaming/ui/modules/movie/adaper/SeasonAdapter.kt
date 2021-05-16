package com.emamagic.moviestreaming.ui.modules.movie.adaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emamagic.moviestreaming.databinding.ItemSeasonBinding
import com.emamagic.moviestreaming.data.db.entity.SeasonEntity
import com.squareup.picasso.Picasso

class SeasonAdapter constructor(val interaction: Interaction):
    ListAdapter<SeasonEntity, SeasonAdapter.SeasonViewHolder>(SeasonListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val binding = ItemSeasonBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return SeasonViewHolder(binding ,interaction)
    }


    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class SeasonViewHolder
    constructor(
        private val binding: ItemSeasonBinding,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SeasonEntity) {
            binding.apply {
                itemView.setOnClickListener { interaction.onSeasonClicked(item) }
                numberSeason.text = "Season : ${item.season}"
                countEpisodes.text = "Episodes : ${item.episode}"
                Picasso.get().load(item.imageLink).resize(400 ,400).into(imgSeason)
            }
        }
    }

    interface Interaction{
        fun onSeasonClicked(item: SeasonEntity)
    }
}