package com.bondidos.task6.models.Factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bondidos.task6.models.NowPlayingViewModel

class NowPlayingViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NowPlayingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NowPlayingViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}