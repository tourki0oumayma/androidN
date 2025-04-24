package com.isimed.myapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isimed.myapplication.Screen.Analyse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.isimed.myapplication.repository.AnalysesRepository
import com.isimed.myapplication.repository.FakeDataRepository


class AnalysesViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = AnalysesRepository(application)

    private val _analyses = MutableStateFlow<List<Analyse>>(emptyList())
    val analyses: StateFlow<List<Analyse>> get() = _analyses

    fun getAnalyses() {
        viewModelScope.launch {
            try {
                val result = repository.getAnalyses()
                _analyses.value = result
            } catch (e: Exception) {
                // gestion d’erreur
            }
        }
    }
}
