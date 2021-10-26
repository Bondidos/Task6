package com.bondidos.task6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.bondidos.task6.utils.Resource
import com.bondidos.task6.utils.Status.ERROR
import com.bondidos.task6.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private var navHostFragment: View? = null

    private var isAbleToFinish = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navHostFragment = findViewById(R.id.navHostFragment)
        setObservers()
    }

    private fun setObservers() {

        mainViewModel.navigateToFragment.observe(this){
            isAbleToFinish = false
            it?.getContentIfNotHandled()?.let{
                navToSongFragment()
            }
        }

        mainViewModel.isConnected.observe(this) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    ERROR ->  showErrorToast(result)
                    else -> Unit
                }
            }
        }

        mainViewModel.networkError.observe(this) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    ERROR -> showErrorToast(result)
                    else -> Unit
                }
            }
        }
    }

    private fun showErrorToast(result: Resource<Boolean>){
        Toast.makeText(
            this,
            result.message ?: "An unknown error occurred",
            Toast.LENGTH_LONG)
            .show()
    }

    private fun navToSongFragment(){
        isAbleToFinish = false
        navHostFragment?.findNavController()?.navigate(
            R.id.globalActionToSongFragment
        )
    }
    private fun navToListFragment(){
        isAbleToFinish = true
        navHostFragment?.findNavController()?.navigate(
            R.id.navToListFragment
        )
    }

    override fun onBackPressed() {
        if(isAbleToFinish) finish() else navToListFragment()
    }
}