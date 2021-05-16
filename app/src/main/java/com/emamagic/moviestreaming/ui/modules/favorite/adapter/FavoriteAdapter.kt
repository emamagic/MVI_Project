package com.emamagic.moviestreaming.ui.modules.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.databinding.ItemFavoriteBinding
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.ui.base.BaseDiffCallback
import com.emamagic.moviestreaming.ui.modules.home.contract.CategoryType
import com.squareup.picasso.Picasso

class FavoriteAdapter constructor(private val interaction: Interaction) :
    ListAdapter<MovieEntity, FavoriteAdapter.FavoriteViewHolder>(FavoriteDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding, interaction)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }


    class FavoriteViewHolder(
        private val binding: ItemFavoriteBinding,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieEntity) = with(binding) {
            itemView.setOnClickListener { interaction.onFavoriteItemClicked(item) }
            Picasso.get().load(item.imageLink).resize(400, 400).into(imgMovie)
            nameMovie.text = item.name
            nameDirector.text = item.director
            rateImdb.text = "IMDb:${item.imdbRate}"
            if (item.categoryName == CategoryType.SERIES) {
                published.text = "Episodes :${item.episode}"
                imgHour.setImageResource(R.drawable.ic_baseline_folder_special_24)
            } else {
                published.text = "Published :${item.published}"
                imgHour.setImageResource(R.drawable.ic_baseline_access_time_24)
            }
        }
    }


    interface Interaction {
        fun onFavoriteItemClicked(item: MovieEntity)
    }

}