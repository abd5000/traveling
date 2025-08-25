package com.example.myadmin

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId="notification_channel"
const val channel_name="com.example.myadmin"
class MyFirebaseMessagingService:FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
      if (message.notification !=null){
          generateNotification(message.notification!!.title!!,message.notification!!.body!!)
      }
    }
    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, message: String):RemoteViews{
        val remoteView=RemoteViews("com.example.myadmin",R.layout.notification)
        remoteView.setTextViewText(R.id.titel,title)
        remoteView.setTextViewText(R.id.description,message)
        remoteView.setImageViewResource(R.id.app_logo,R.mipmap.pen)
        return remoteView
    }
    fun generateNotification(title:String,message:String){
        val intent= Intent(this,HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent=PendingIntent.getActivity(this,0,intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)


        val builder=Notification.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.notification)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            . setContentText(message)
            .build()
//        builder=builder.setContent(getRemoteView(title,message))

        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel=NotificationChannel(channelId, channel_name,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(1, builder)

    }

    override fun onNewToken(token: String) {
       Log.d("newtoken2", "Refreshed token: $token")
    }
}