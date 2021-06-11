package com.example.moviedbjm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AlertDialog


class NetworkChangeReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val connMgr = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connMgr.isDefaultNetworkActive) {

            context.let {
                AlertDialog.Builder(it)
                    .setMessage(context.getString(R.string.alert_dialog_title))
                    .setTitle(context.getString(R.string.alert_dialog_message))
                    .setIcon(R.drawable.ic_baseline_warning_black)
                    .create().show()
            }
        }

    }
}