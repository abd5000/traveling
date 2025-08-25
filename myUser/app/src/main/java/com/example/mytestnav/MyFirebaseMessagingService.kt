package com.example.mytestnav

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mytestnav.profily.NotificationActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


const val channelId="notification_channel"
const val channel_name="com.example.myadmin"
class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification !=null){
            generateNotification(message.notification!!.title!!,message.notification!!.body!!)
        }
    }
    fun generateNotification(title:String,message:String){
        val intent= Intent(this, NotificationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent= PendingIntent.getActivity(this,0,intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)


        val builder= Notification.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.notifications_24)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            . setContentText(message)
            .build()

        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel= NotificationChannel(channelId, channel_name, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(1, builder)

    }

    override fun onNewToken(token: String) {
        Log.d("newtoken2", "Refreshed token: $token")
        val sharedPref =getSharedPreferences("fcmToken", Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putString("FCMToken",token)
        editor.apply()
    }
}