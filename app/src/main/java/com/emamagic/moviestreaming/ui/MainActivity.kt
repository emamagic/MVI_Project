package com.emamagic.moviestreaming.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.databinding.ActivityMainBinding
import com.emamagic.moviestreaming.provider.conectivity.*
import com.emamagic.moviestreaming.util.setupWithNavController
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , DialogListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var connectionLiveData: ConnectionLiveData
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
      //  radiusBottomNavigation(R.dimen.small_radius)
      //  destinationBottomNavigation()

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState

        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this) { isNetworkAvailable ->
            if (!isNetworkAvailable) { createConnectivityAlert() }
        }

        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                if (!DoesNetworkHaveInternet.execute()) {
                    withContext(Dispatchers.Main) {
                        createConnectivityAlert()
                    }
                }
            }
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = binding.bottomNav
        val navGraphIds = listOf(R.navigation.nav_graph_home, R.navigation.nav_graph_player)
        bottomNavigationView.setOnNavigationItemReselectedListener { /* NO-OP */ }
        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this) { navController ->
           // setupActionBarWithNavController(navController)
        }
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun createConnectivityAlert(){
        ConnectivityFragment.newInstance(
            "Please check your connectivity \n Do you wanna continue \n offline ?",
            "yes",
            "exit",
            AlarmType.WARNING,
            false
        ).show(supportFragmentManager, null)
    }

    private fun destinationBottomNavigation() {
        // addOnDestinationChangedListener to navGraph (this work for both of drawerLayout & bottomNavigationView)
        binding.navHostFragment.findNavController()
            .addOnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.homeFragment -> {
                        binding.bottomNav.visibility = View.VISIBLE
                    }
                    R.id.verifyFragment -> {
                        binding.bottomNav.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.bottomNav.visibility = View.GONE
                    }
                }
            }
    }

    private fun radiusBottomNavigation(radius: Int) {
        val radiusValue = resources.getDimension(radius)
        val bottomNavigationViewBackground = binding.bottomNav.background as MaterialShapeDrawable
        bottomNavigationViewBackground.shapeAppearanceModel =
            bottomNavigationViewBackground.shapeAppearanceModel.toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, radiusValue)
                .setTopLeftCorner(CornerFamily.ROUNDED, radiusValue)
                .build()
    }

    override fun onAccept(dialog: DialogFragment?) {
        dialog?.dismiss()
    }

    override fun onDecline(dialog: DialogFragment?) {
        finish()
    }

}