package com.isimed.myapplication.ViewModel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.isimed.myapplication.util.TokenManager
import com.isimed.myapplication.repository.FakeDataRepository

import com.isimed.myapplication.Screen.Laboratoire

import kotlinx.coroutines.launch
class FakeDataViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = FakeDataRepository(application)

    // UI state variables
    var token = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var error = mutableStateOf("")
    private val tokenManager = TokenManager(application)  // Create an instance of TokenManager
    var Laboratories = mutableStateOf<List<Laboratoire>>(emptyList())


    //
    fun fetchLaboratories() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = repository.Laboratoires()
                if (response.isSuccessful) {
                    Laboratories.value = response.body() ?: emptyList()
                } else {
                    error.value = "Erreur : ${response.code()}"
                }
            } catch (e: Exception) {
                error.value = "Erreur : ${e.localizedMessage}"
            }
            isLoading.value = false
        }
    }

}