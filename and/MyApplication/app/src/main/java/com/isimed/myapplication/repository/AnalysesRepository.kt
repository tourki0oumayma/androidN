package com.isimed.myapplication.repository


import android.app.Application
import com.isimed.myapplication.Screen.Analyse
import com.isimed.myapplication.network.ApiService
import com.isimed.myapplication.network.RetrofitInstance

class AnalysesRepository(application : Application) {
    private val api: ApiService = RetrofitInstance.createApi(application)

    suspend fun getAnalyses(): List<Analyse> {
        return api.getAnalyses()
    }
}
