package com.emamagic.moviestreaming.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.databinding.ItemNewMovieBinding
import com.emamagic.moviestreaming.databinding.ItemTopMovieImdbBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.squareup.picasso.Picasso

class MovieAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {

        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTopMovieImdbBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return MovieViewHolder(binding ,interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<MovieEntity>) {
        differ.submitList(list)
        notifyDataSetChanged()
    }

    class MovieViewHolder
    constructor(
        private val binding: ItemTopMovieImdbBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieEntity) = with(itemView) {
            setOnClickListener {
                interaction?.onMovieClicked(adapterPosition, item)
            }
            binding.nameTopMovieImdb.text = item.name
            binding.timeMovieImdb.text = item.time
            Picasso.get().load(item.link_img).into(binding.imgTopMovieImdb)
            if (item.rank.isNotEmpty()) {
                binding.rankMovie.visibility = View.VISIBLE
                binding.rankMovie.text = "Rank:${item.rank}"
            }else binding.rankMovie.visibility = View.GONE

        }
    }

    interface Interaction {
        fun onMovieClicked(position: Int, item: MovieEntity)
    }
}
