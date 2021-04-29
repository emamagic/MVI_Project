package com.emamagic.moviestreaming.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.databinding.ItemSliderBinding
import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.squareup.picasso.Picasso

class SliderAdapter(private val context: Context, private val sliders: List<SliderEntity>): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_slider, container, false)
        val binding = ItemSliderBinding.bind(view)
        binding.nameSlider.text = sliders[position].name
        binding.publishedSlider.text = sliders[position].published
        binding.timeSlider.text = sliders[position].time
        Picasso.get().load(sliders[position].imageLink).placeholder(R.drawable.video_placeholder).into(binding.imgSlider)
        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return sliders.size

    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}