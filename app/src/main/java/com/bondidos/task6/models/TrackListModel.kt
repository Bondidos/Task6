package com.bondidos.task6.models

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bondidos.task6.recycler.TrackItem
import com.bumptech.glide.Glide
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class TrackListModel(context: Context) : ViewModel() {
    // todo no need list of bitmaps???!
    // collection of bitmaps
    val bitmaps = HashMap<String, Bitmap>(5)

    private var _trackList: MutableLiveData<List<TrackItem>> = MutableLiveData<List<TrackItem>>()
    val trackList: LiveData<List<TrackItem>> get() = _trackList

    init{
        getTrackListFromJson(context)
    }
    private fun getTrackListFromJson(context: Context){
        val moshi = Moshi.Builder()
            .build()

        val arrayType = Types.newParameterizedType(List::class.java,TrackItem::class.java)
        val adapter: JsonAdapter<List<TrackItem>> = moshi.adapter(arrayType)

        val file = "playlist.json"
        val myPlayList = context.assets.open(file).bufferedReader().use { it.readText() }
        _trackList.value = adapter.fromJson(myPlayList)

        // todo no need list of bitmaps???!
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                try {
                    _trackList.value?.forEach {
                        val bitmap = Glide.with(context)
                            .asBitmap()
                            .load(it.bitmapUri)
                            .submit()
                            .get()
                        bitmaps[it.bitmapUri] = bitmap
                    }
                }
                catch (e: Exception){
                    //todo make mock for internet connection failed
                    e.stackTrace
                }
            }
        }
    }
}
