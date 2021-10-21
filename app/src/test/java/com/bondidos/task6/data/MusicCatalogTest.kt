package com.bondidos.task6.data

import com.bondidos.task6.di.AppModule
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import org.junit.Assert.*
import org.junit.Test
import javax.inject.Inject

@EntryPoint
class MusicCatalogTest{

    @Inject
    lateinit var musicCatalog: MusicCatalog

    @Test
    fun getMusicCatalog(){

        assert(musicCatalog.getAllSongs().isNotEmpty())
    }
}