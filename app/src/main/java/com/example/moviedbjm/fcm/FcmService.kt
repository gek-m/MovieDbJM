package com.example.moviedbjm.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.moviedbjm.MainActivity
import com.example.moviedbjm.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService : FirebaseMessagingService() {

    companion object {
        private const val CHANNEL_ID = "CHANNEL_ID"
        private const val NOTIFY_ID = 1
        private const val REQUEST_CODE = 0
    }

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                CHANNEL_ID,
                getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = getString(R.string.notification_channel_description)
            }.also {
                notificationManager.createNotificationChannel(it)
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.notification?.apply(::showNotification)
    }

    private fun showNotification(notification: RemoteMessage.Notification) {

        val resultIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivities(
            this,
            REQUEST_CODE,
            arrayOf(resultIntent),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle(notification.title)
            setStyle(
                NotificationCompat
                    .BigTextStyle()
                    .bigText(notification.body)
            )
            setSmallIcon(R.drawable.ic_favorite_black)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }.also {
            notificationManager.notify(NOTIFY_ID, it.build())
        }
    }
}