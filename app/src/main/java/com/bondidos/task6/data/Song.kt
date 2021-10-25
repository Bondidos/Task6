package com.bondidos.task6.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Song (
    val title: String,
    val artist: String,
    val bitmapUri: String,
    val trackUri: String,
    val duration: Long
)
