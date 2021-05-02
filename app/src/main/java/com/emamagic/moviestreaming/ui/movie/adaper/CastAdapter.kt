package com.emamagic.moviestreaming.ui.movie.adaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emamagic.moviestreaming.databinding.ItemCastBinding
import com.emamagic.moviestreaming.db.entity.CastEntity
import com.squareup.picasso.Picasso

class CastAdapter:
    ListAdapter<CastEntity, CastAdapter.CastViewHolder>(CastListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return CastViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class CastViewHolder
    constructor(
        private val binding: ItemCastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CastEntity) {
            binding.apply {
                nameCast.text = item.name
                Picasso.get().load(item.imageLink).resize(400 ,400).into(imgCast)
            }
        }
    }
}