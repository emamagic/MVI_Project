package com.emamagic.moviestreaming.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.emamagic.moviestreaming.databinding.ActivityMainBinding
import com.emamagic.moviestreaming.ui.connectivity.AlarmType
import com.emamagic.moviestreaming.ui.connectivity.ConnectivityFragment
import com.emamagic.moviestreaming.ui.connectivity.DialogListener
import com.emamagic.moviestreaming.util.helper.conectivity.ConnectionLiveData
import com.emamagic.moviestreaming.util.helper.conectivity.DoesNetworkHaveInternet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() ,DialogListener{

    private lateinit var binding: ActivityMainBinding
    lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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


    private fun createConnectivityAlert(){
        ConnectivityFragment.newInstance(
            "Please check your connectivity \n Do you wanna continue \n offline ?",
            "yes",
            "exit",
            AlarmType.WARNING,
            false
        ).show(supportFragmentManager, null)
    }

    override fun onAccept(dialog: DialogFragment?) {
        dialog?.dismiss()
    }

    override fun onDecline(dialog: DialogFragment?) {
        finish()
    }

}