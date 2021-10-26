package com.bondidos.task6.utils

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_DURATION
import android.view.View
import android.view.animation.AnimationUtils
import com.bondidos.task6.R
import com.bondidos.task6.data.Song

fun Long.displayTime(): String {

    val m = this / 1000 % 3600 / 60
    val s = this / 1000 % 60

    return "${displaySlot(m)}:${displaySlot(s)}"
}

private fun displaySlot(count: Long): String {
    return if (count / 10L > 0) {
        "$count"
    } else "0$count"
}

fun MediaMetadataCompat.toSong(): Song? {

    val duration = this.getLong(METADATA_KEY_DURATION)

    return description?.let {
        Song(
            it.title.toString(),
            it.subtitle.toString(),
            it.iconUri.toString(),
            it.mediaUri.toString(),
            duration
        )
    }
}

fun View.playClickAnimation(){
    this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.click))
}
