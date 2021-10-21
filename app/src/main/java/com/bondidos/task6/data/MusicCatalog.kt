package com.bondidos.task6.data

import android.content.Context
import com.bumptech.glide.Glide
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicCatalog @Inject constructor(private val context: Context) {

    fun getAllSongs(): List<Song> {

        val moshi = Moshi.Builder()
            .build()
        val arrayType = Types.newParameterizedType(List::class.java, Song::class.java)
        val adapter: JsonAdapter<List<Song>> = moshi.adapter(arrayType)

        val file = "playlist.json"

        val myJson = context.assets.open(file).bufferedReader().use { it.readText() }

        return adapter.fromJson(myJson) ?: emptyList()

    }
}