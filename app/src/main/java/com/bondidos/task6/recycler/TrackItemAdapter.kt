package com.bondidos.task6.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bondidos.task6.R
import com.bondidos.task6.databinding.TrackItemBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.track_item.view.*

class TrackItemAdapter(
    private val itemClickedListener: (TrackItem) -> Unit
) : ListAdapter<TrackItem, TrackViewHolder>(TrackItem.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TrackItemBinding.inflate(inflater,parent,false)
        return TrackViewHolder(binding,itemClickedListener)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = getItem(position)
        holder.trackItem = item
        holder.title.text = item.title
        holder.subtitle.text = item.artist

        Glide.with(holder.image)
            .load(item.bitmapUri)
            .placeholder(R.drawable.ic_baseline_music_note_24)
            .into(holder.image)
    }
}

class TrackViewHolder(
    binding: TrackItemBinding,
    itemClickedListener: (TrackItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    val title = binding.title
    val subtitle = binding.subtitle
    val image = binding.albumArt

    var trackItem: TrackItem? = null

    init{
        binding.root.setOnClickListener {
            trackItem?.let { itemClickedListener(it) }
        }
    }
}
