package com.dasaevcompany.upax.utilities

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dasaevcompany.upax.R
import com.google.android.gms.maps.model.LatLng
import java.util.*

class NotificationsChannelUtil {

    fun channelLocation (context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val channel = NotificationChannel(
                "Location",
                "Notification location",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Channel notification for update location"
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun notifyLocationUpdate(context: Context, location : LatLng) {



        val notificationManager = NotificationManagerCompat.from(context)
        val notify = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(context, "Location")
        } else {
            NotificationCompat.Builder(context)
        }

        notify.setSmallIcon(R.drawable.ic_location)
            .setContentTitle("Localización Actualizada")
            .setContentText("${location.latitude} ${location.longitude}")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .priority = NotificationCompat.PRIORITY_HIGH

        notificationManager.notify(Random().nextInt(10000), notify.build())
    }


}