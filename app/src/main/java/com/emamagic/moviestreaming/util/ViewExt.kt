package com.emamagic.moviestreaming.util

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.emamagic.moviestreaming.R
import com.google.android.material.bottomnavigation.BottomNavigationView

inline fun EditText.onTextChange(crossinline listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            listener(s.toString())
        }
    })
}

inline fun SearchView.onQueryTextListener(crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?) = true
        override fun onQueryTextChange(newText: String?): Boolean { listener(newText.orEmpty())
            return true
        }
    })
}

inline fun SearchView.onQueryTextSubmitListener(crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean { listener(query.orEmpty())
            return true
        }
        override fun onQueryTextChange(newText: String?) = true })
}

inline fun DrawerLayout.onDrawerListener(crossinline listener: (Float ,View) -> Unit) {
    this.addDrawerListener(object: DrawerLayout.DrawerListener{
        override fun onDrawerSlide(drawerView: View, slideOffset: Float) { listener(slideOffset ,drawerView) }
        override fun onDrawerOpened(drawerView: View) {}
        override fun onDrawerClosed(drawerView: View) {}
        override fun onDrawerStateChanged(newState: Int) {}
    })
}


fun Fragment.toasty(title: String ,@ToastyMode selectedMode: Int? = null) {
    val layout = layoutInflater.inflate(
        R.layout.toast_layout,
        requireView().findViewById(R.id.toast_root)
    )
    when (selectedMode) {

        ToastyMode.MODE_TOAST_SUCCESS -> {
            layout.findViewById<ImageView>(R.id.toast_img)
                .setImageResource(R.drawable.ic_corroct_toast)
            layout.findViewById<ConstraintLayout>(R.id.toast_root)
                .setBackgroundResource(R.drawable.bg_corroct_toast)
        }
        ToastyMode.MODE_TOAST_WARNING -> {
            layout.findViewById<ImageView>(R.id.toast_img)
                .setImageResource(R.drawable.ic_warning_toast)
            layout.findViewById<ConstraintLayout>(R.id.toast_root)
                .setBackgroundResource(R.drawable.bg_warning_toast)
            layout.findViewById<TextView>(R.id.toast_txt)
                .setTextColor(resources.getColor(R.color.black))
        }
        ToastyMode.MODE_TOAST_ERROR -> {
            layout.findViewById<ImageView>(R.id.toast_img)
                .setImageResource(R.drawable.ic_error_toast)
            layout.findViewById<ConstraintLayout>(R.id.toast_root)
                .setBackgroundResource(R.drawable.bg_error_toast)
        }
        else -> {
            Toast.makeText(requireContext(), title, Toast.LENGTH_LONG).show()
        }

    }

    layout.findViewById<TextView>(R.id.toast_txt).text = title
    if (selectedMode != null) {
        Toast(requireActivity()).apply {
            setGravity(Gravity.BOTTOM, 0, 100)
            duration = Toast.LENGTH_LONG
            view = layout
        }.show()
    }
}


fun Fragment.toasty(@StringRes titleId: Int ,@ToastyMode selectedMode: Int? = null) {
    val layout = layoutInflater.inflate(
        R.layout.toast_layout,
        requireView().findViewById(R.id.toast_root)
    )
    when (selectedMode) {

        ToastyMode.MODE_TOAST_SUCCESS -> {
            layout.findViewById<ImageView>(R.id.toast_img)
                .setImageResource(R.drawable.ic_corroct_toast)
            layout.findViewById<ConstraintLayout>(R.id.toast_root)
                .setBackgroundResource(R.drawable.bg_corroct_toast)
        }
        ToastyMode.MODE_TOAST_WARNING -> {
            layout.findViewById<ImageView>(R.id.toast_img)
                .setImageResource(R.drawable.ic_warning_toast)
            layout.findViewById<ConstraintLayout>(R.id.toast_root)
                .setBackgroundResource(R.drawable.bg_warning_toast)
            layout.findViewById<TextView>(R.id.toast_txt)
                .setTextColor(resources.getColor(R.color.black))
        }
        ToastyMode.MODE_TOAST_ERROR -> {
            layout.findViewById<ImageView>(R.id.toast_img)
                .setImageResource(R.drawable.ic_error_toast)
            layout.findViewById<ConstraintLayout>(R.id.toast_root)
                .setBackgroundResource(R.drawable.bg_error_toast)
        }
        else -> {
            Toast.makeText(requireContext(), resources.getString(titleId), Toast.LENGTH_LONG).show()
        }

    }

    layout.findViewById<TextView>(R.id.toast_txt).text = resources.getString(titleId)
    if (selectedMode != null) {
        Toast(requireActivity()).apply {
            setGravity(Gravity.BOTTOM, 0, 100)
            duration = Toast.LENGTH_LONG
            view = layout
        }.show()
    }
}

val <T> T.exhaustive: T
    get() = this

/** Navigation Component with Bottom Navigation setUp  */

fun BottomNavigationView.setupWithNavController(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent
): LiveData<NavController> {

    // Map of tags
    val graphIdToTagMap = SparseArray<String>()
    // Result. Mutable live data with the selected controlled
    val selectedNavController = MutableLiveData<NavController>()

    var firstFragmentGraphId = 0

    // First create a NavHostFragment for each NavGraph ID
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )

        // Obtain its id
        val graphId = navHostFragment.navController.graph.id

        if (index == 0) {
            firstFragmentGraphId = graphId
        }

        // Save to the map
        graphIdToTagMap[graphId] = fragmentTag

        // Attach or detach nav host fragment depending on whether it's the selected item.
        if (this.selectedItemId == graphId) {
            // Update livedata with the selected graph
            selectedNavController.value = navHostFragment.navController
            attachNavHostFragment(fragmentManager, navHostFragment, index == 0)
        } else {
            detachNavHostFragment(fragmentManager, navHostFragment)
        }
    }

    // Now connect selecting an item with swapping Fragments
    var selectedItemTag = graphIdToTagMap[this.selectedItemId]
    val firstFragmentTag = graphIdToTagMap[firstFragmentGraphId]
    var isOnFirstFragment = selectedItemTag == firstFragmentTag

    // When a navigation item is selected
    setOnNavigationItemSelectedListener { item ->
        // Don't do anything if the state is state has already been saved.
        if (fragmentManager.isStateSaved) {
            false
        } else {
            val newlySelectedItemTag = graphIdToTagMap[item.itemId]
            if (selectedItemTag != newlySelectedItemTag) {
                // Pop everything above the first fragment (the "fixed start destination")
                fragmentManager.popBackStack(
                    firstFragmentTag,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                        as NavHostFragment

                // Exclude the first fragment tag because it's always in the back stack.
                if (firstFragmentTag != newlySelectedItemTag) {
                    // Commit a transaction that cleans the back stack and adds the first fragment
                    // to it, creating the fixed started destination.
                    fragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.nav_default_enter_anim,
                            R.anim.nav_default_exit_anim,
                            R.anim.nav_default_pop_enter_anim,
                            R.anim.nav_default_pop_exit_anim
                        )
                        .attach(selectedFragment)
                        .setPrimaryNavigationFragment(selectedFragment)
                        .apply {
                            // Detach all other Fragments
                            graphIdToTagMap.forEach { _, fragmentTagIter ->
                                if (fragmentTagIter != newlySelectedItemTag) {
                                    detach(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
                                }
                            }
                        }
                        .addToBackStack(firstFragmentTag)
                        .setReorderingAllowed(true)
                        .commit()
                }
                selectedItemTag = newlySelectedItemTag
                isOnFirstFragment = selectedItemTag == firstFragmentTag
                selectedNavController.value = selectedFragment.navController
                true
            } else {
                false
            }
        }
    }

    // Optional: on item reselected, pop back stack to the destination of the graph
    setupItemReselected(graphIdToTagMap, fragmentManager)

    // Handle deep link
    setupDeepLinks(navGraphIds, fragmentManager, containerId, intent)

    // Finally, ensure that we update our BottomNavigationView when the back stack changes
    fragmentManager.addOnBackStackChangedListener {
        if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag)) {
            this.selectedItemId = firstFragmentGraphId
        }

        // Reset the graph if the currentDestination is not valid (happens when the back
        // stack is popped after using the back button).
        selectedNavController.value?.let { controller ->
            if (controller.currentDestination == null) {
                controller.navigate(controller.graph.id)
            }
        }
    }
    return selectedNavController
}

private fun getFragmentTag(index: Int) = "bottomNavigation#$index"

private fun obtainNavHostFragment(
    fragmentManager: FragmentManager,
    fragmentTag: String,
    navGraphId: Int,
    containerId: Int
): NavHostFragment {
    // If the Nav Host fragment exists, return it
    val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
    existingFragment?.let { return it }

    // Otherwise, create it and return it.
    val navHostFragment = NavHostFragment.create(navGraphId)
    fragmentManager.beginTransaction()
        .add(containerId, navHostFragment, fragmentTag)
        .commitNow()
    return navHostFragment
}

private fun attachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment,
    isPrimaryNavFragment: Boolean
) {
    fragmentManager.beginTransaction()
        .attach(navHostFragment)
        .apply {
            if (isPrimaryNavFragment) {
                setPrimaryNavigationFragment(navHostFragment)
            }
        }
        .commitNow()
}

private fun detachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment
) {
    fragmentManager.beginTransaction()
        .detach(navHostFragment)
        .commitNow()
}

private fun BottomNavigationView.setupItemReselected(
    graphIdToTagMap: SparseArray<String>,
    fragmentManager: FragmentManager
) {
    setOnNavigationItemReselectedListener { item ->
        val newlySelectedItemTag = graphIdToTagMap[item.itemId]
        val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                as NavHostFragment
        val navController = selectedFragment.navController
        // Pop the back stack to the start destination of the current navController graph
        navController.popBackStack(
            navController.graph.startDestination, false
        )
    }
}

private fun BottomNavigationView.setupDeepLinks(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent
) {
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )
        // Handle Intent
        if (navHostFragment.navController.handleDeepLink(intent)
            && selectedItemId != navHostFragment.navController.graph.id
        ) {
            this.selectedItemId = navHostFragment.navController.graph.id
        }
    }
}

private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount) {
        if (getBackStackEntryAt(index).name == backStackName) {
            return true
        }
    }
    return false
}