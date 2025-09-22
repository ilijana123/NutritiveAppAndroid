package com.example.nutritiveapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiveapp.data.model.response.AuthResponse
import com.example.nutritiveapp.data.model.request.LoginRequest
import com.example.nutritiveapp.data.remote.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class LoginViewModel : ViewModel() {
    fun login(
        request: LoginRequest,
        onSuccess: (AuthResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.userApi.login(request).awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { authResponse ->
                        onSuccess(authResponse)
                    } ?: onError("Empty response body")
                } else {
                    onError("Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
