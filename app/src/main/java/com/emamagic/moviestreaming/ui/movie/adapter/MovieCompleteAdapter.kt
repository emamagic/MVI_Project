package com.emamagic.moviestreaming.ui.movie.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.databinding.ItemTopMovieImdbCompleteBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.ui.home.contract.CategoryType
import com.squareup.picasso.Picasso

class MovieCompleteAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {

        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTopMovieImdbCompleteBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return MovieCompleteViewHolder(binding ,interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieCompleteViewHolder -> {
                holder.bind(differ.currentList[position])
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

    class MovieCompleteViewHolder
    constructor(
        val binding: ItemTopMovieImdbCompleteBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieEntity) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onMovieSelected(adapterPosition, item)
            }

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

    interface Interaction {
        fun onMovieSelected(position: Int, item: MovieEntity)
    }
}
