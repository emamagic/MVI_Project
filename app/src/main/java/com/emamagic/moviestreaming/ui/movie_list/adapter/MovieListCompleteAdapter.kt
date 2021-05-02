package com.emamagic.moviestreaming.ui.movie_list.adapter

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

class MovieListCompleteAdapter(val interaction: MovieInteraction) :
    ListAdapter<MovieEntity , MovieListCompleteAdapter.MovieListViewHolder>(MovieListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val binding = ItemTopMovieImdbCompleteBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return MovieListViewHolder(binding ,interaction)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class MovieListViewHolder
    constructor(
        val binding: ItemTopMovieImdbCompleteBinding,
        private val interaction: MovieInteraction
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieEntity) = with(itemView) {
            binding.root.setOnClickListener { interaction.onMovieClicked(item) }
            binding.imgFav.setOnClickListener { interaction.favoriteClicked(item.id!!) }
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
            if (item.isFavorite) binding.imgFav.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.imgFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)

        }
    }

    interface MovieInteraction {
        fun onMovieClicked(item: MovieEntity)
        fun favoriteClicked(id: Long)
    }

}
