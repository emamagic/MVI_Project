package com.emamagic.moviestreaming.ui.video_player

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.databinding.FragmentPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class FragmentVideoPlayer : Fragment(R.layout.fragment_player) {

    private lateinit var player: PlayerView
    private val args: FragmentVideoPlayerArgs by navArgs()
    private var _binding: FragmentPlayerBinding? = null
    private val binding: FragmentPlayerBinding get() = _binding!!

    private lateinit var videoPlayer: SimpleExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player = view.findViewById(R.id.video_view)
        initializePlayer(args.videoLink)
    }

    private fun initializePlayer(uri: String) {
        videoPlayer = SimpleExoPlayer.Builder(requireContext()).build()
        player.player = videoPlayer
        buildMediaSource(uri).let {
            videoPlayer.prepare(it)
        }
    }

    private fun buildMediaSource(uri: String): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(requireContext(), "sample")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(uri))
    }

    override fun onResume() {
        super.onResume()
        videoPlayer.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        videoPlayer.playWhenReady = false
        releasePlayer()
    }

    private fun releasePlayer() {
        videoPlayer.release()
    }

    override fun onDestroyView() {
        super.onDestroyView()
         _binding = null
    }
}