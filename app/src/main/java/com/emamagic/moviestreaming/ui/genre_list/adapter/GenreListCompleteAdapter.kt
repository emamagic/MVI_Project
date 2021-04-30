package com.emamagic.moviestreaming.ui.genre_list.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.emamagic.moviestreaming.databinding.ItemGenreCompleteBinding
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.squareup.picasso.Picasso

class GenreListCompleteAdapter :
    ListAdapter<GenreEntity , GenreListCompleteAdapter.GenreViewHolder>(GenreListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreCompleteBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return GenreViewHolder(binding)
    }


    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class GenreViewHolder
    constructor(
        private val binding: ItemGenreCompleteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GenreEntity) = with(itemView) {

            binding.nameGenre.text = item.name
            Picasso.get().load(item.imageLing).resize(400 ,200).into(binding.imgGenre)

        }
    }

}
