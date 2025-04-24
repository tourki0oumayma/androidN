package com.isimed.myapplication.ViewModel


import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonSyntaxException
import com.isimed.myapplication.repository.AuthRepository
import com.isimed.myapplication.util.TokenManager
import kotlinx.coroutines.launch

class AuthViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = AuthRepository(application)
    // UI state variables
    var token = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var error = mutableStateOf("")
    private val tokenManager = TokenManager(application)  // Create an instance of TokenManager


    fun registre(firstName: String, lastName: String, email: String, password: String) {

        viewModelScope.launch {
                isLoading.value = true
                try {

                    val response = repository.registre(firstName, lastName, email, password)
                    Log.d("RegisterData", "First Name: $firstName, Last Name: $lastName, Email: $email, Password: $password")

                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            token.value = responseBody.token
                            Log.d("test", "Token: ${token.value}")
                            tokenManager.saveToken(token.value)
                        } else {
                            error.value = "Empty response from server"
                        }

                    } else {
                        error.value = "Registration failed: ${response.code()}"
                    }

                } catch (e: Exception) {
                    error.value = "Error: ${e.localizedMessage}"
                }
                isLoading.value = false
            }
        }


    fun login(email: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = repository.login(email, password)

                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        token.value = responseBody.token
                        Log.d("AuthViewModel", "Login successful, Token: ${token.value}")
                        // Save the token using TokenManager
                        tokenManager.saveToken(token.value)
                    } else {
                        error.value = "Empty response from server"
                        Log.e("AuthViewModel", "Login failed: Empty response")
                    }

                } else {
                    val errorResponse = response.errorBody()?.string() ?: "Unknown error"
                    error.value = "Login failed: ${response.code()} - $errorResponse"
                    Log.e("AuthViewModel", "Login failed: ${response.code()} - $errorResponse")
                }

            } catch (e: JsonSyntaxException) {
                error.value = "Invalid JSON format received"
                Log.e("AuthViewModel", "JSON Parsing Error", e)
            } catch (e: Exception) {
                error.value = "Error: ${e.localizedMessage}"
                Log.e("AuthViewModel", "Login error", e)
            }
            isLoading.value = false
        }
    }
}