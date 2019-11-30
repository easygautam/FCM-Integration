package com.example.demo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Service class to handle Firebase events like- receive message and device token change
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = javaClass.simpleName

    /**
     * This method will be called on message received sent by server or anywhere else on FCM
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)

            // Show notification
            sendNotification(
                remoteMessage.data["title"]!!,
                remoteMessage.data["text"]!!,
                remoteMessage.data["image"]!!
            )
        }

    }

    /**
     * This method will be called on device token refreshed (See Firebase device token change policy for more details)
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // Update token on server or store where you want
    }


    /**
     * Show notification to user with title, description and notification image
     */
    private fun sendNotification(title: String, message: String, imageUrl: String) {

        // Sets an ID for the notification, so it can be updated.
        val notifyID = 1
        val CHANNEL_ID = "my_channel_01" // The id of the channel.

        val name: CharSequence =
            getString(R.string.channel_name) // The user-visible name of the channel.

        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create notification channel its required with and after oreo
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mNotificationManager.createNotificationChannel(mChannel)
        }

        // Define a intent for a task that you want to perform on notification click i.e - start activity, etc.
        val emptyIntent = Intent()
        val pendingIntent =
            PendingIntent.getActivity(this, 1, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Build a notification
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent) //Required on Gingerbread

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notifyID, mBuilder.build())

        /**
         * Load image in bitmap from url
         */
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    Log.d(TAG, "Image loaded")
                    // add large image into notification
                    mBuilder.setLargeIcon(resource)
                    mBuilder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    // update notification with image
                    notificationManager.notify(notifyID, mBuilder.build())
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

    }

}