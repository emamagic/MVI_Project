package com.emamagic.moviestreaming.ui.movie_list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.databinding.ItemTopMovieImdbCompleteBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.ui.home.contract.CategoryType
import com.squareup.picasso.Picasso

class MovieListViewHolder
constructor(
    val binding: ItemTopMovieImdbCompleteBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieEntity) = with(itemView) {
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