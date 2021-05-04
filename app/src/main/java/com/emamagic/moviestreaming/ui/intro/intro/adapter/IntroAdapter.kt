package com.emamagic.moviestreaming.ui.intro.intro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.emamagic.moviestreaming.databinding.ItemIntroBinding
import com.emamagic.moviestreaming.network.dto.IntroDto
import com.squareup.picasso.Picasso

class IntroAdapter(val list: List<IntroDto>): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ItemIntroBinding.inflate(LayoutInflater.from(container.context) ,container ,false)
        val view = binding.root
        binding.txtDescription.text = list[position].description
        Picasso.get().load(list[position].imageLink).into(binding.imgIntro)
        container.addView(view)
        return view
    }

    override fun getCount() = list.size

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}