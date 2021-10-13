package com.bondidos.task6.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bondidos.task6.databinding.TrackListFragmentBinding
import com.bondidos.task6.models.TrackListModel
import com.bondidos.task6.models.Factories.TrackListModelFactory
import com.bondidos.task6.models.MainActivityViewModel
import com.bondidos.task6.recycler.TrackItemAdapter

class TrackListFragment : Fragment() {

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val trackListViewModel by viewModels<TrackListModel> {
        TrackListModelFactory(requireContext())
    }
    private val listAdapter = TrackItemAdapter{clickedItem ->
        mainActivityViewModel.browseToItem(clickedItem)
    }
    private var _binding: TrackListFragmentBinding? = null
    private val binding: TrackListFragmentBinding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TrackListFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initObservers()
    }

    private fun initRecycler() {
        with(binding){
            recycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = listAdapter
            }
        }
    }

    private fun initObservers(){
        trackListViewModel.trackList.observe(viewLifecycleOwner){
            listAdapter.submitList(it)
        }
    }

    companion object {
        fun newInstance() = TrackListFragment()
    }
}