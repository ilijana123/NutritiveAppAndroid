package com.example.nutritiveapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.nutritiveapp.data.local.SessionManager
import com.example.nutritiveapp.data.model.UserSession
import com.example.nutritiveapp.data.model.request.UpdateUserRequest
import com.example.nutritiveapp.data.model.response.AuthResponse
import com.example.nutritiveapp.data.remote.RetrofitInstance
import kotlinx.coroutines.launch

class SessionViewModel(app: Application) : AndroidViewModel(app) {

    private val sessionManager = SessionManager(app)

    private val _session = MutableStateFlow(
        UserSession(
            id = sessionManager.getUserId(),
            email = sessionManager.getEmail(),
            username = sessionManager.getUsername(),
            firstName = sessionManager.getFirstName(),
            lastName = sessionManager.getLastName(),
            token = sessionManager.getToken()
        )
    )
    val session = _session.asStateFlow()

    fun login(authResponse: AuthResponse) {
        sessionManager.saveSession(
            authResponse.id,
            authResponse.email,
            authResponse.username,
            authResponse.firstName,
            authResponse.lastName,
            authResponse.token
        )
        Log.d("SessionManager", "Saved token: ${authResponse.token}")

        _session.value = UserSession(
            id = authResponse.id,
            email = authResponse.email,
            username = authResponse.username,
            firstName = authResponse.firstName,
            lastName = authResponse.lastName,
            token = authResponse.token
        )
    }

    fun logout() {
        sessionManager.clearSession()
        _session.value = UserSession()
    }

    fun updateUser(request: UpdateUserRequest, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val currentSession = _session.value
                val response = RetrofitInstance.userApi.updateUser(
                    userId = currentSession.id ?: return@launch,
                    request = request,
                    token = "Bearer ${currentSession.token}"
                )

                login(response)
                onResult(true)
            } catch (e: Exception) {
                Log.e("SessionViewModel", "Failed to update user", e)
                onResult(false)
            }
        }
    }
}