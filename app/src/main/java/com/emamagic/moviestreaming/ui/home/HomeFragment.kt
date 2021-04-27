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
            SliderStatus.EmptyList -> toasty("emptylist")
            is SliderStatus.FetchList -> toasty("fecth list ${viewState.sliderStatus.sliders}")
            is SliderStatus.Loading -> toasty("loading ${viewState.sliderStatus.isLoading}")
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