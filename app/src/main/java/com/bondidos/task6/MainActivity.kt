package com.bondidos.task6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bondidos.task6.databinding.MainActivityBinding
import com.bondidos.task6.viewModel.MainViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setObservers()
    }

    private fun setObservers() {

        mainViewModel.navigateToFragment.observe(this){
            it?.let{
                navToSongFragment()
            }
        }
    }

    private fun navToSongFragment(){

        findViewById<View>(R.id.navHostFragment).findNavController().navigate(
            R.id.globalActionToSongFragment
        )
    }
}