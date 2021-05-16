package com.emamagic.moviestreaming.ui.base

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.ui.modules.favorite.adapter.FavoriteAdapter

abstract class BaseListAdapter<T : Any, VB: ViewBinding> constructor(
    private val interaction: Interaction<T>? = null
) : ListAdapter<T, BaseListAdapter.BaseViewHolder<VB,T>>(BaseDiffCallback<T>()) {


    override fun onBindViewHolder(holder: BaseViewHolder<VB,T>, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bindItem(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB,T> {
        return BaseViewHolder(inflateItemBinding(parent ,viewType),interaction)
    }

    override fun getItemCount() = currentList.size

    class BaseViewHolder<VB: ViewBinding,T>(
        private val binding: VB,
        private val interaction: Interaction<T>?
    ) : RecyclerView.ViewHolder(binding.root){
        fun bindItem(item: T)= with(binding) {
          root.setOnClickListener { interaction?.onItemClicked(item) }
            // TODO: 5/16/2021 Access developer to here
        }

    }

    abstract fun inflateItemBinding(parent: ViewGroup, viewType: Int): VB

    interface Interaction<T> {
        fun onItemClicked(item: T)
    }

}