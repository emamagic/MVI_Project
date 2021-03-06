package com.emamagic.moviestreaming.ui.modules.home.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.databinding.ItemGenreBinding
import com.emamagic.moviestreaming.data.db.entity.GenreEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class GenreAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GenreEntity>() {

        override fun areItemsTheSame(oldItem: GenreEntity, newItem: GenreEntity) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GenreEntity, newItem: GenreEntity) = oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return MovieViewHolder(binding ,interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<GenreEntity>) {
        differ.submitList(list)
    }

    class MovieViewHolder
    constructor(
        private val binding: ItemGenreBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GenreEntity) = with(itemView) {
            setOnClickListener {
                interaction?.onGenreClicked(item)
            }
            binding.nameGenre.text = item.name
            Picasso.get().load(item.imageLing).resize(400,400).placeholder(R.drawable.ic_movie_placeholder).into(binding.imgGenre ,object: Callback{
                override fun onSuccess() {
                    interaction?.onShimmerStopGenre()
                }

                override fun onError(e: Exception?) {

                }
            })
        }
    }

    interface Interaction {
        fun onGenreClicked(item: GenreEntity)
        fun onShimmerStopGenre()
    }
}
