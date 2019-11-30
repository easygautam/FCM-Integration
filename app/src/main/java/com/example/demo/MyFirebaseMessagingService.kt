package com.example.demo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = javaClass.simpleName
    override fun onMessageReceived(remoteMessage: RemoteMessage) { // ...
// TODO(developer): Handle FCM messages here.
// Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.from)
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            //            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }

            sendNotification(remoteMessage.data["title"]!!, remoteMessage.data["text"]!!)
        }
        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(
                TAG,
                "Message Notification Body: " + remoteMessage.notification!!.body
            )
        }
        // Also if you intend on generating your own notifications as a result of a received FCM
// message, here is where that should be initiated. See sendNotification method below.
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        // If you want to send messages to this application instance or
// manage this apps subscriptions on the server side, send the
// Instance ID token to your app server.
//        sendRegistrationToServer(token);
    }



    private fun sendNotification(title: String, message: String) {

        // Sets an ID for the notification, so it can be updated.
        val notifyID = 1
        val CHANNEL_ID = "my_channel_01" // The id of the channel.

        val name: CharSequence =
            getString(R.string.channel_name) // The user-visible name of the channel.

        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mNotificationManager.createNotificationChannel(mChannel)
        }

        val emptyIntent = Intent()
        val pendingIntent =
            PendingIntent.getActivity(this, 1, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val mBuilder =  NotificationCompat.Builder(this,  CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent) //Required on Gingerbread

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notifyID, mBuilder.build())

    }

}