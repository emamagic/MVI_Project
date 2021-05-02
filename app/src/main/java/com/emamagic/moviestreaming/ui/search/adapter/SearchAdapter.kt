package com.emamagic.moviestreaming.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emamagic.moviestreaming.databinding.ItemSearchBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.network.dto.MovieDto
import com.squareup.picasso.Picasso

class SearchAdapter constructor(private val interaction: Interaction): ListAdapter<MovieDto ,SearchAdapter.SearchViewHolder>(SearchDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return SearchViewHolder(binding ,interaction)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }


    class SearchViewHolder constructor(
        private val binding: ItemSearchBinding,
        private val interaction: Interaction
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieDto){
            binding.apply {
                itemView.setOnClickListener { interaction.onSearchItemClicked(item) }
                name.text = item.name
                Picasso.get().load(item.imageLink).into(img)
            }
        }
    }

    interface Interaction {
        fun onSearchItemClicked(item: MovieDto)
    }

}