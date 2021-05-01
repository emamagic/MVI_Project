package com.emamagic.moviestreaming.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.databinding.ItemSliderBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.emamagic.moviestreaming.util.Const
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso


class SliderAdapter(private val context: Context) : SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {

    private var mSliderItems = ArrayList<SliderEntity>()

    fun renewItems(sliderItems: List<SliderEntity>) {
        mSliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: SliderEntity) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemSliderBinding.inflate(inflater ,parent ,false)
        return SliderAdapterVH(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        viewHolder?.bind(mSliderItems[position])
    }

    override fun getCount() = mSliderItems.size


    class SliderAdapterVH(val binding: ItemSliderBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {
        fun bind(item: SliderEntity) {

            binding.apply {
                nameSlider.text = item.name
                publishedSlider.text = item.published
                timeSlider.text = item.time
                Picasso.get().load(item.imageLink).resize(900,500).placeholder(R.drawable.video_placeholder).into(imgSlider)
            }
        }
    }

}