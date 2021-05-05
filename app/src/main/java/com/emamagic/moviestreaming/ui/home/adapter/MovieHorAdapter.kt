package com.emamagic.moviestreaming.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.databinding.ItemPopularMovieBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class MovieHorAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {

        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
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
    }

    class MovieViewHolder
    constructor(
        private val binding: ItemPopularMovieBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieEntity) = with(itemView) {
            setOnClickListener {
                interaction?.onMovieHorClicked(item)
            }
            binding.nameTopMovieImdb.text = item.name
            binding.timeMovieImdb.text = item.time
            Picasso.get().load(item.imageLink).resize(600,400).placeholder(R.drawable.ic_movie_placeholder).into(binding.imgTopMovieImdb ,object:
                Callback {
                override fun onSuccess() {
                    interaction?.onShimmerStopMovieHor(item)
                }

                override fun onError(e: Exception?) {

                }
            })

        }
    }

    interface Interaction {
        fun onMovieHorClicked(item: MovieEntity)
        fun onShimmerStopMovieHor(item: MovieEntity)
    }
}
