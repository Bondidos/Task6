package com.bondidos.task6.models.Factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bondidos.task6.models.TrackListModel

class TrackListModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackListModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TrackListModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}