package com.bondidos.task6.utils

import android.support.v4.media.MediaMetadataCompat
import android.view.View
import android.view.animation.AnimationUtils
import com.bondidos.task6.R
import com.bondidos.task6.data.Song
import com.bondidos.task6.other.constants.SONG_DURATION

fun Long.displayTime(): String {

    val h = this / 1000 / 3600
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
    return description?.let {
        Song(
            it.title.toString(),
            it.subtitle.toString(),
            it.iconUri.toString(),
            it.mediaUri.toString(),
            it.extras?.getLong(SONG_DURATION) ?: 0L
        )
    }
}

fun View.playClickAnimation(){
    this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.click))
}
