package com.isimed.myapplication.repository

import android.app.Application
import com.isimed.myapplication.Screen.Doctor
import com.isimed.myapplication.network.ApiService
import com.isimed.myapplication.network.RetrofitInstance
import retrofit2.Response

class DoctorRepository(application : Application) {

    private val api: ApiService = RetrofitInstance.createApi(application)

    suspend fun getDoctors(): Response<List<Doctor>> {
        return api.getDoctor()
    }
    suspend fun likeDoctor(id: Int): Response<String> {
        return api.likeDoctor(id)
    }



}