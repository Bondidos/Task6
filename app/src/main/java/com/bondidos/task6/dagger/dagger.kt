package com.bondidos.task6.dagger

import android.app.Application
import android.content.Context
import com.bondidos.task6.MainActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [MainActivity::class])
interface AppComponent{

    fun context(): Context
}

@Module
class NowPlayingViewModel(private val application: Application){
    @Provides
    @Singleton
    fun appContext (): Context = application
}