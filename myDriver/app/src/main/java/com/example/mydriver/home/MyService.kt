package com.example.mydriver.home

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import com.example.mydriver.HomeActivity
import com.example.mydriver.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket

class MyService : Service() {


    private val SERVEICE_ID = 1
    private val OPEN_APP_ACTION = "OPEN_APP"

    private lateinit var socket: Socket
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()
       val sharedPreferences = getSharedPreferences("onUserId", Context.MODE_PRIVATE)
         val driverId=sharedPreferences.getInt("UserId",0)
        Log.i("driverId", "$driverId")
        // Check for location permission
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permission from the user
            return
        }

        // Create Socket.IO connection
        socket = IO.socket("http://192.168.43.77:3001")
        socket.connect()

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Create notification channel and notification
        val intent = Intent(this, HomeActivity::class.java)
        intent.action = OPEN_APP_ACTION
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val CHANNEL_ID = "My_channel_1"
        val channel = NotificationChannel(CHANNEL_ID, "Default", NotificationManager.IMPORTANCE_DEFAULT)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        val notification = Notification.Builder(this, CHANNEL_ID).apply {
            setContentTitle("Driver")
            setContentText("Tracking is on" +
                    "")
            setSmallIcon(R.drawable.notification)
            addAction(R.drawable.notification, "Open App", pendingIntent)
        }.build()

        // Start foreground service
        startForeground(SERVEICE_ID, notification)

        // Start requesting location updates
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 200).apply {
            setIntervalMillis(200)
        }.build()

        fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    // Send location over Socket.IO
                    // Log the state of the Socket.IO connection
                   // Log.d("MyServiceLocation", "Socket.IO connection state: ${socket.connected()}")
                    // Log the location data
                    val data = mapOf(
//
                        "id" to driverId,
                        "longitude" to location.longitude,
                        "latitude" to location.latitude
                    )

                    // Convert the data object to JSON
                    val dataJson = Gson().toJson(data)

                    // Send data over Socket.IO
                    socket.emit("driverCoordinates", dataJson)

                    Log.d("MyServiceLocation", "Location and ID sent: $dataJson")

                }
            }
        }, null)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Stop requesting location updates and close Socket.IO connection
        fusedLocationClient.removeLocationUpdates(object : LocationCallback() {})
        socket.disconnect()
    }
}