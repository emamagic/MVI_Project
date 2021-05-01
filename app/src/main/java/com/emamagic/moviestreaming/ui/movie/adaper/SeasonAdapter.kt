package com.emamagic.moviestreaming.ui.movie.adaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emamagic.moviestreaming.databinding.ItemCastBinding
import com.emamagic.moviestreaming.databinding.ItemGenreCompleteBinding
import com.emamagic.moviestreaming.databinding.ItemSeasonBinding
import com.emamagic.moviestreaming.db.entity.CastEntity
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.SeasonEntity
import com.emamagic.moviestreaming.ui.genre_list.adapter.GenreListCompleteAdapter
import com.emamagic.moviestreaming.ui.genre_list.adapter.GenreListDiffCallback
import com.squareup.picasso.Picasso

class SeasonAdapter:
    ListAdapter<SeasonEntity, SeasonAdapter.SeasonViewHolder>(SeasonListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val binding = ItemSeasonBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return SeasonViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class SeasonViewHolder
    constructor(
        private val binding: ItemSeasonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SeasonEntity) {
            binding.apply {
                numberSeason.text = "Season : ${item.season}"
                countEpisodes.text = "Episodes : ${item.episode}"
                Picasso.get().load(item.imageLink).resize(400 ,400).into(imgSeason)
            }
        }
    }
}