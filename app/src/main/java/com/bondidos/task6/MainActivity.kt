package com.bondidos.task6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bondidos.task6.models.MainActivityViewModel
import com.bondidos.task6.ui.fragments.NowPlayingFragment
import com.bondidos.task6.ui.fragments.TrackListFragment

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            navigateToListFragment()
        }


        // Observe MainActivityViewModel.navigateToFragment for Events that request a fragment swap.
        viewModel.navigateToFragment.observe(this) {
            it?.getContentIfNotHandled()?.let { fragmentRequest ->
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.container, fragmentRequest.fragment, fragmentRequest.tag
                )
                if (fragmentRequest.backStack) transaction.addToBackStack(null)
                transaction.commit()
            }
        }

        /**
         * Observe [MainActivityViewModel.navigateToTrackItem] for [Event]s indicating
         * the user has requested to browse to a different [MediaItemData].
         */
        viewModel.navigateToTrackItem.observe(this) {
            it?.getContentIfNotHandled()?.let { mediaId ->
                navigateToTrackFragment(mediaId)
            }
        }

    }

    private fun navigateToListFragment() {
        val fragment: TrackListFragment = TrackListFragment.newInstance()
        viewModel.showFragment(fragment, true)
    }


    private fun navigateToTrackFragment(mediaId: String) {
        val fragment: NowPlayingFragment = NowPlayingFragment.newInstance(mediaId)

        // If this is not the top level media (root), we add it to the fragment
        // back stack, so that actionbar toggle and Back will work appropriately:
        viewModel.showFragment(fragment, true)
    }


    private fun isRootId(mediaId: String) = false//mediaId == viewModel.rootMediaId.value

    /*private fun getBrowseFragment(mediaId: String): NowPlayingFragment? {
        return supportFragmentManager.findFragmentByTag(mediaId) as NowPlayingFragment?
    }*/

}