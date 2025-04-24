package com.isimed.myapplication.repository

import android.app.Application
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import com.isimed.myapplication.Screen.Laboratoire
import com.isimed.myapplication.network.ApiService
import com.isimed.myapplication.network.RetrofitInstance
import retrofit2.Response

class FakeDataRepository(application: Application) {
    private val api: ApiService = RetrofitInstance.createApi(application)

    suspend fun Laboratoires(): Response<List<Laboratoire>> {
        return api.getLaboratoires()
    }

    suspend fun getLaboratoires(): Response<List<Laboratoire>> = api.getLaboratoires()

    suspend fun uploadImage(filePart: MultipartBody.Part): Response<ResponseBody> {
        return api.uploadFile(filePart)
    }
    suspend fun getImages(): Response<List<String>> = api.getFiles()


}
