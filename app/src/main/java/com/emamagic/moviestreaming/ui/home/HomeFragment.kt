package com.emamagic.moviestreaming.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentHomeBinding
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding ,HomeState ,HomeEffect ,HomeEvent ,HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater ,container ,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setEvent(HomeEvent.GetSlider)

    }

    override fun renderViewState(viewState: HomeState) {
        when(viewState.sliderStatus) {
            SliderStatus.EmptyList -> Timber.e("emptyList")
            is SliderStatus.FetchList -> Timber.e("fetch ${viewState.sliderStatus.sliders}")
            is SliderStatus.Loading -> Timber.e("loading")
        }.exhaustive
    }

    override fun renderViewEffect(viewEffect: HomeEffect) {
        when(viewEffect) {
            is HomeEffect.Navigate -> TODO()
            is HomeEffect.ShowSnackBar -> TODO()
            is HomeEffect.ShowToast -> toasty(viewEffect.message ,ToastyMode.MODE_TOAST_ERROR)
        }.exhaustive
    }


}