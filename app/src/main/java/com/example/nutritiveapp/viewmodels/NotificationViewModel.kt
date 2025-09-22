
package com.example.nutritiveapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nutritiveapp.data.local.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotificationViewModel(app: Application) : AndroidViewModel(app) {
    private val sessionManager = SessionManager(app)

    private val _notificationsEnabled =
        MutableStateFlow(sessionManager.getNotificationsEnabled())
    val notificationsEnabled = _notificationsEnabled.asStateFlow()

    fun toggleNotifications(enabled: Boolean) {
        _notificationsEnabled.value = enabled
        sessionManager.saveNotificationsEnabled(enabled)
    }

    fun requiresPermission(): Boolean {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU
    }

    fun disableNotifications() {
        _notificationsEnabled.value = false
        sessionManager.saveNotificationsEnabled(false)
    }
}