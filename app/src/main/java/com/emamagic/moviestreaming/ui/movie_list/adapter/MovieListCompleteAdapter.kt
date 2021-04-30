package com.emamagic.moviestreaming.ui.movie_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.emamagic.moviestreaming.databinding.ItemTopMovieImdbCompleteBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity

class MovieListCompleteAdapter :
    ListAdapter<MovieEntity ,MovieListViewHolder>(MovieListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val binding = ItemTopMovieImdbCompleteBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return MovieListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }


}
