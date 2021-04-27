package com.emamagic.moviestreaming.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentHomeBinding
import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.emamagic.moviestreaming.ui.adapter.SliderAdapter
import com.emamagic.moviestreaming.ui.home.contract.HomeEffect
import com.emamagic.moviestreaming.ui.home.contract.HomeEvent
import com.emamagic.moviestreaming.ui.home.contract.HomeState
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeState, HomeEffect, HomeEvent, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var timer: Timer


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setEvent(HomeEvent.GetSlider)


    }

    override fun renderViewState(viewState: HomeState) {
        when (viewState.sliderStatus) {
            SliderStatus.EmptyList -> Timber.e("emptyList")
            is SliderStatus.FetchList -> setUpSlider(requireContext(), viewState.sliderStatus.sliders)
            is SliderStatus.Loading -> if (viewState.sliderStatus.isLoading) showLoading() else hideLoading()
        }.exhaustive
    }

    override fun renderViewEffect(viewEffect: HomeEffect) {
        when (viewEffect) {
            is HomeEffect.Navigate -> TODO()
            is HomeEffect.ShowSnackBar -> TODO()
            is HomeEffect.ShowToast -> toasty(viewEffect.message, ToastyMode.MODE_TOAST_ERROR)
        }.exhaustive
    }


    private fun setUpSlider(context: Context, list: List<SliderEntity>) {
        sliderAdapter = SliderAdapter(context, list)
        binding?.slider?.adapter = sliderAdapter
        binding?.tabLayout?.setupWithViewPager(binding?.slider, true)
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    if (binding?.slider?.currentItem!! < list.size - 1)
                        binding?.slider?.currentItem = binding?.slider?.currentItem!! + 1
                    else binding?.slider?.currentItem = 0
                }
            }
        }, 3000, 3000)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        timer?.let {
            it.cancel()
            it.purge()
        }
    }

}