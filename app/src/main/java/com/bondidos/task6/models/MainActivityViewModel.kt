package com.bondidos.task6.models

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.bondidos.task6.recycler.TrackItem
import com.bondidos.task6.utils.Event

class MainActivityViewModel() : ViewModel() {

   /* // navigate to Item
    private val _trackItem = MutableLiveData<Event<String>>()
    val trackItem: LiveData<Event<String>> get() = _trackItem
*/
    // Used to notify the MainActivity that the main content fragment needs to be swapped.
    val navigateToFragment: LiveData<Event<FragmentNavigationRequest>> get() = _navigateToFragment
    private val _navigateToFragment = MutableLiveData<Event<FragmentNavigationRequest>>()

    /**
     * [navigateToTrackItem] acts as an "event", rather than state. [Observer]s
     * are notified of the change as usual with [LiveData], but only one [Observer]
     * will actually read the data. For more information, check the [Event] class.
     */
    val navigateToTrackItem: LiveData<Event<String>> get() = _navigateToTrackItem
    private val _navigateToTrackItem = MutableLiveData<Event<String>>()

    /**
     * Convenience method used to swap the fragment shown in the main activity
     *
     * @param fragment the fragment to show
     * @param backStack if true, add this transaction to the back stack
     * @param tag the name to use for this fragment in the stack
     */
    /*fun showFragment(fragment: Fragment, backStack: Boolean = true, tag: String? = null) {
        _navigateToFragment.value = Event(FragmentNavigationRequest(fragment, backStack, tag))
    }*/
    fun showFragment(fragment: Fragment, backStack: Boolean = true){
        _navigateToFragment.value = Event(FragmentNavigationRequest(fragment, backStack, null))
    }
    /**
     * This posts a browse [Event] that will be handled by the
     * observer in [MainActivity].
     */
    fun browseToItem(trackItem: TrackItem) {
        _navigateToTrackItem.value = Event(trackItem.title)
    }
}

/**
 * Helper class used to pass fragment navigation requests between MainActivity
 * and its corresponding ViewModel.
 */
data class FragmentNavigationRequest(
    val fragment: Fragment,
    val backStack: Boolean = false,
    val tag: String? = null
)