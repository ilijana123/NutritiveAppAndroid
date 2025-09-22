package com.example.nutritiveapp.data.local

import android.content.Context
import androidx.core.content.edit

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveNotificationsEnabled(enabled: Boolean) {
        prefs.edit { putBoolean(Companion.KEY_NOTIFICATIONS_ENABLED, enabled) }
    }

    fun getNotificationsEnabled(): Boolean {
        return prefs.getBoolean(Companion.KEY_NOTIFICATIONS_ENABLED, false)
    }

    fun saveSession(
        id: Long,
        email: String,
        username: String,
        firstName: String,
        lastName: String,
        token: String
    ) {
        prefs.edit {
            putLong("user_id", id)
                .putString("user_email", email)
                .putString("user_username", username)
                .putString("user_first_name", firstName)
                .putString("user_last_name", lastName)
                .putString("jwt_token", token)
        }
    }

    fun clearSession() {
        prefs.edit { clear() }
    }

    fun getUserId(): Long? = prefs.getLong("user_id", -1L).takeIf { it != -1L }
    fun getEmail(): String? = prefs.getString("user_email", null)
    fun getUsername(): String? = prefs.getString("user_username", null)
    fun getFirstName(): String? = prefs.getString("user_first_name", null)
    fun getLastName(): String? = prefs.getString("user_last_name", null)
    fun getToken(): String? = prefs.getString("jwt_token", null)

    companion object {
        private const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
    }
}

