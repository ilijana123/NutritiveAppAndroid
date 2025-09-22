package com.example.nutritiveapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiveapp.data.remote.RetrofitInstance
import com.example.nutritiveapp.data.model.request.SignUpRequest
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class SignUpViewModel : ViewModel() {
    fun signUp(
        request: SignUpRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.userApi.createUser(request).awaitResponse()
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}