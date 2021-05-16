package com.emamagic.moviestreaming.ui.modules.episode_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emamagic.moviestreaming.data.db.entity.EpisodeEntity
import com.emamagic.moviestreaming.ui.base.BaseFragment
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.databinding.FragmentEpisodeListBinding
import com.emamagic.moviestreaming.ui.modules.episode_list.adapter.EpisodeListAdapter
import com.emamagic.moviestreaming.ui.modules.episode_list.contract.EpisodeListEvent
import com.emamagic.moviestreaming.ui.modules.episode_list.contract.EpisodeListState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeListFragment: BaseFragment<FragmentEpisodeListBinding, EpisodeListState, CommonEffect, EpisodeListEvent, EpisodeListViewModel>(),
    EpisodeListAdapter.Interaction{

    override val viewModel: EpisodeListViewModel by viewModels()
    private val args: EpisodeListFragmentArgs by navArgs()
    private lateinit var episodeAdapter: EpisodeListAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEpisodeListBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        episodeAdapter = EpisodeListAdapter(this)
        viewModel.setEvent(EpisodeListEvent.GetEpisodes(args.seasonId))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtSeasons.text = "Season : ${args.seasonId}"
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }

    override fun renderViewState(viewState: EpisodeListState) {
        viewState.episodes?.let { setUpEpisodesRecycler(it) }
    }


    private fun setUpEpisodesRecycler(list: List<EpisodeEntity>) {
        binding.recyclerViewEpisodes.adapter = episodeAdapter
        binding.recyclerViewEpisodes.setHasFixedSize(true)
        binding.recyclerViewEpisodes.itemAnimator = null
        episodeAdapter.submitList(list)
    }

    override fun onEpisodeClicked(item: EpisodeEntity) {
        viewModel.setEvent(EpisodeListEvent.PlayEpisodeClicked(item.videoLink!!))
    }

}