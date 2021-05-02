package com.emamagic.moviestreaming.ui.genre_type.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.emamagic.moviestreaming.databinding.ItemGenreCompleteBinding
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.SeasonEntity
import com.emamagic.moviestreaming.ui.genre_list.adapter.GenreListAdapter
import com.squareup.picasso.Picasso

class GenreTypeCompleteAdapter constructor(private val interaction: Interaction) :
    ListAdapter<GenreEntity , GenreTypeCompleteAdapter.GenreTypeViewHolder>(GenreTypeDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreTypeViewHolder {
        val binding = ItemGenreCompleteBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return GenreTypeViewHolder(binding, interaction)
    }


    override fun onBindViewHolder(holderList: GenreTypeViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holderList.bind(currentItem)
        }
    }

    class GenreTypeViewHolder
    constructor(
        private val binding: ItemGenreCompleteBinding,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GenreEntity) = with(itemView) {
            itemView.setOnClickListener { interaction.onGenreClicked(item) }
            binding.nameGenre.text = item.name
            Picasso.get().load(item.imageLing).resize(400 ,200).into(binding.imgGenre)

        }
    }
    interface Interaction{
        fun onGenreClicked(item: GenreEntity)
    }
}
