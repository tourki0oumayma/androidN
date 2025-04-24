package com.isimed.myapplication.ViewModel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.isimed.myapplication.Screen.Doctor
import com.isimed.myapplication.repository.DoctorRepository
import kotlinx.coroutines.launch

class DoctorViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = DoctorRepository(application)
    var token = mutableStateOf("")
    var doctors = mutableStateOf<List<Doctor>>(emptyList())
    var isLoading = mutableStateOf(false)
    var error = mutableStateOf("")

    fun fetchDoctors() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = repository.getDoctors()
                if (response.isSuccessful) {
                    doctors.value = response.body() ?: emptyList()
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
