package com.bondidos.task6.recycler

import androidx.recyclerview.widget.DiffUtil
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrackItem(
    val title: String,
    val artist: String,
    val bitmapUri: String,
    val trackUri: String,
    val duration: String
) {
    companion object{
        const val PLAYBACK_RES_CHANGED = 1

        val diffCallback = object : DiffUtil.ItemCallback<TrackItem>() {
            override fun areItemsTheSame(
                oldItem: TrackItem,
                newItem: TrackItem
            ): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: TrackItem, newItem: TrackItem) =
                oldItem.title == newItem.title && oldItem.duration == newItem.duration

            override fun getChangePayload(oldItem: TrackItem, newItem: TrackItem) =
                if (oldItem.duration != newItem.duration) {
                    PLAYBACK_RES_CHANGED
                } else null
        }
    }
}