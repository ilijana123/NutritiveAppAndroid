package com.example.nutritiveapp.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiveapp.data.dto.AllergenDTO
import com.example.nutritiveapp.data.model.request.AssignAllergensRequest
import com.example.nutritiveapp.data.remote.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class AllergenViewModel : ViewModel() {
    var allergens by mutableStateOf<List<AllergenDTO>>(emptyList())
    val selectedAllergens = mutableStateListOf<Long>()

    fun searchAllergens(query: String) {
        if (query.isBlank()) {
            loadAllergens()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val results = RetrofitInstance.allergenApi.searchAllergensByName(query)
                    allergens = results
                } catch (e: Exception) {
                    e.printStackTrace()
                    allergens = emptyList()
                }
            }
        }
    }

    fun loadAllergens() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = RetrofitInstance.allergenApi.getAllAllergens()
                allergens = result
            } catch (e: Exception) {
                e.printStackTrace()
                allergens = emptyList()
            }
        }
    }

    fun loadUserAllergens(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val resp = RetrofitInstance.allergenApi.getUserAllergens(userId).awaitResponse()
            if (resp.isSuccessful) {
                val userAllergens = resp.body().orEmpty()
                selectedAllergens.clear()
                selectedAllergens.addAll(userAllergens.map { it.id })
            }
        }
    }

    fun toggleSelection(allergenId: Long) {
        if (selectedAllergens.contains(allergenId)) {
            selectedAllergens.remove(allergenId)
        } else {
            selectedAllergens.add(allergenId)
        }
    }

    fun saveUserAllergens(userId: Long, onSuccess: () -> Unit, onError: (String) -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val body = AssignAllergensRequest(allergenIds = selectedAllergens.toList())
            Log.d("AssignAllergensRequest", Gson().toJson(body))
            val resp = RetrofitInstance.allergenApi.assignAllergens(userId, body).execute()
            if (resp.isSuccessful) {
                launch(Dispatchers.Main) { onSuccess() }
            } else {
                val msg = resp.errorBody()?.string().orEmpty()
                launch(Dispatchers.Main) { onError("Save failed: ${resp.code()} $msg") }
            }
        }
    }
}