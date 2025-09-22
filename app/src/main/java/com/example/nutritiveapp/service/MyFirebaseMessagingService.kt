package com.example.nutritiveapp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.nutritiveapp.R
import com.example.nutritiveapp.data.model.request.DeviceTokenRequest
import com.example.nutritiveapp.data.remote.RetrofitInstance
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New token: $token")
        sendTokenToBackend(token)
    }

    private fun sendTokenToBackend(token: String) {
        RetrofitInstance.deviceApi.registerDevice(DeviceTokenRequest(token))
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Log.d("FCM", "Token registered with backend")
                    } else {
                        Log.e("FCM", "Failed token registration: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.e("FCM", "Error: ${t.message}")
                }
            })
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title ?: "Allergen Alert"
        val body = remoteMessage.notification?.body ?: "This product contains allergens!"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "allergen_alerts"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Allergen Alerts", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.baseline_warning_24)
            .build()

        notificationManager.notify(0, notification)
    }
}
