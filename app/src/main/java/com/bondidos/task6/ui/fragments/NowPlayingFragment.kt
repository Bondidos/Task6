package com.bondidos.task6.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bondidos.task6.R
import com.bondidos.task6.models.NowPlayingViewModel

const val NOW_PLAYING = "nowPlaying"

class NowPlayingFragment : Fragment() {

    companion object {
        fun newInstance(ItemId: String) = NowPlayingFragment().apply {
            arguments?.putString(NOW_PLAYING,ItemId)
        }
    }

    private lateinit var viewModel: NowPlayingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.now_playing_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NowPlayingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}