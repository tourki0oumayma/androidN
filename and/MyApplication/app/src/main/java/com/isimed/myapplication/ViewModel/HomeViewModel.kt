package com.isimed.myapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.isimed.myapplication.util.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val tokenManager = TokenManager(application)

    private val _token = MutableStateFlow(tokenManager.getToken() ?: "")
    val token = _token.asStateFlow()

    fun logout() {
        tokenManager.clearToken()
        _token.value = ""
    }
}

