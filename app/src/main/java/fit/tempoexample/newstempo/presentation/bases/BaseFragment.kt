package fit.tempoexample.newstempo.presentation.bases

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import fit.tempoexample.newstempo.R
import fit.tempoexample.newstempo.utils.NetworkUtil


abstract class BaseFragment(layoutRes: Int) : Fragment(layoutRes) {
    private var networkFlag = true

    override fun onResume() {
        super.onResume()
        registerBroadcasts()
        internetCheckProcess()
    }

    override fun onPause() {
        unregisterBroadcasts()
        super.onPause()
    }

    open fun internetCheckProcess() {
        if (NetworkUtil.isNetworkAvailable(requireContext()) && !networkFlag) {
            activity?.let {
                Toast.makeText(requireContext(), "Network is Available", Toast.LENGTH_SHORT).show()
            }
            networkFlag = true
        }
        if (!NetworkUtil.isNetworkAvailable(requireContext()) && networkFlag) {
            activity?.let {
                Toast.makeText(requireContext(), "Network is not Available", Toast.LENGTH_SHORT)
                    .show()
            }
            networkFlag = false
        }
    }

    private fun registerBroadcasts() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun unregisterBroadcasts() {
        requireContext().unregisterReceiver(broadcastReceiver)
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            when (intent?.action) {
                ConnectivityManager.CONNECTIVITY_ACTION -> internetCheckProcess()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}