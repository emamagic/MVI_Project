package com.emamagic.moviestreaming.ui.genre_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.databinding.ItemTopMovieImdbCompleteBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.ui.home.contract.CategoryType
import com.squareup.picasso.Picasso

class GenreListAdapter constructor(val interaction: Interaction):
    ListAdapter<MovieEntity, GenreListAdapter.GenreListViewHolder>(GenreListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListViewHolder {
        val binding = ItemTopMovieImdbCompleteBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return GenreListViewHolder(binding, interaction)
    }


    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class GenreListViewHolder
    constructor(
        private val binding: ItemTopMovieImdbCompleteBinding,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieEntity) {
            binding.apply {
                binding.root.setOnClickListener { interaction.onMovieClicked(item) }
                binding.nameMovie.text = item.name
                binding.nameDirector.text = "Director :${item.director}"
                binding.rankMovie.text = "Rank:${item.rank}"
                binding.rateImdb.text = "IMDb:${item.imdbRate}"
                binding.timeMovie.text = item.time
                Picasso.get().load(item.imageLink).resize(400,400).into(binding.imgMovie)
                if (item.categoryName == CategoryType.SERIES) {
                    binding.published.text = "Episodes :${item.episode}"
                    binding.imgHour.setImageResource(R.drawable.ic_baseline_folder_special_24)
                }
                else {
                    binding.published.text = "Published :${item.published}"
                    binding.imgHour.setImageResource(R.drawable.ic_baseline_access_time_24)
                }
                if (item.categoryName == CategoryType.TOP) binding.rankMovie.visibility = View.VISIBLE
                else binding.rankMovie.visibility = View.GONE
            }
        }
    }

    interface Interaction{
        fun onMovieClicked(item: MovieEntity)
    }
}