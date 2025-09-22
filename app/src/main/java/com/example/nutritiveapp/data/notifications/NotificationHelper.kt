package com.example.nutritiveapp.data.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.nutritiveapp.R

object NotificationHelper {
    private const val CHANNEL_ID = "allergen_alerts"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Allergen Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications about allergens in scanned products"
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun showAllergenAlert(context: Context, allergens: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("⚠ Allergen Alert")
            .setContentText("This product contains: $allergens")
            .setSmallIcon(R.drawable.ic_warning)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1001, notification)
    }
}
