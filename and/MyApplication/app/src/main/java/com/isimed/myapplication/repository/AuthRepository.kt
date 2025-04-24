package com.isimed.myapplication.repository

import android.app.Application
import android.util.Log
import com.isimed.myapplication.network.ApiService
import com.isimed.myapplication.network. RegistreRequest
import com.isimed.myapplication.network.LoginRequest
import com.isimed.myapplication.network.LoginResponse
import com.isimed.myapplication.network.RegistreResponse
import com.isimed.myapplication.network.RetrofitInstance
import retrofit2.Response

class AuthRepository(application : Application) {
    private val api: ApiService = RetrofitInstance.createApi(application)
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)
        return api.login(request)
    }

    suspend fun registre(firstName: String, lastName: String, email:String, password:String): Response<RegistreResponse> {
        val request =
            com.isimed.myapplication.network.RegistreRequest(firstName, lastName, email, password)
        Log.d("requesst", "request: {$request}")
        return api.registre(request)
    }



}