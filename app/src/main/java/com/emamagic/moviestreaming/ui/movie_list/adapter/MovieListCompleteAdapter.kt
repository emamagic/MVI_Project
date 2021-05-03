package com.emamagic.moviestreaming.ui.movie_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.databinding.ItemTopMovieImdbCompleteBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.MovieWithFavorite
import com.emamagic.moviestreaming.ui.home.contract.CategoryType
import com.squareup.picasso.Picasso

class MovieListCompleteAdapter(val interaction: MovieInteraction) :
    ListAdapter<MovieWithFavorite, MovieListCompleteAdapter.MovieListViewHolder>(MovieListDiffCallback()) {

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

        fun bind(item: MovieWithFavorite) = with(binding) {
                root.setOnClickListener { interaction.onMovieClicked(item) }
                imgFav.setOnClickListener { interaction.favoriteClicked(item.movie.id!!) }
                nameMovie.text = item.movie.name
                nameDirector.text = "Director :${item.movie.director}"
                rankMovie.text = "Rank:${item.movie.rank}"
                rateImdb.text = "IMDb:${item.movie.imdbRate}"
                timeMovie.text = item.movie.time
                Picasso.get().load(item.movie.imageLink).resize(400,400).into(imgMovie)
                if (item.movie.categoryName == CategoryType.SERIES) {
                    published.text = "Episodes :${item.movie.episode}"
                    imgHour.setImageResource(R.drawable.ic_baseline_folder_special_24)
                }
                else {
                    published.text = "Published :${item.movie.published}"
                    imgHour.setImageResource(R.drawable.ic_baseline_access_time_24)
                }
                if (item.movie.categoryName == CategoryType.TOP) rankMovie.visibility = View.VISIBLE
                else rankMovie.visibility = View.GONE
            if (item.favorites != null) imgFav.setImageResource(R.drawable.ic_baseline_favorite_24)
            else imgFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)



        }
    }

    interface MovieInteraction {
        fun onMovieClicked(item: MovieWithFavorite)
        fun favoriteClicked(id: Long)
    }

}
