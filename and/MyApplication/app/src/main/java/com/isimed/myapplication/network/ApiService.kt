package com.isimed.myapplication.network

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.isimed.myapplication.Screen.Analyse
import com.isimed.myapplication.Screen.Doctor
import com.isimed.myapplication.Screen.Laboratoire

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import java.util.Date

// Data classes for request and response
data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)
data class RegistreRequest(val firstName: String, val  lastName: String ,  val email:String, val password:String)
data class RegistreResponse(val token: String)
data class LaboratoireRequest(val id:Int ,val adresse:String , val nom:String, val telephone:String )
data class LaboratoireResponse(val token: String)
data class doctoreRequest(val id:Int ,val nom:String , val prenom:String, val email:String ,val password:String ,val telephone:String ,val typeUtilisateur:String ,val specialite:String ,val matricule:String,val nbLikes: Int,  val imageName: String)
data class doctorResponse(val token: String)
data class AnalyseRequest(val id:Long ,val nomAnalyse:String ,val prix:Float ,val dateResultat: Date )
data class AnalyseResponse(val token: String)
data class PaymentRequest(
    val amount: Double,
    val note: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone: String,
    val return_url: String?,
    val cancel_url: String?,
    val webhook_url: String,
    val order_id: String?
)
interface ApiService {
    @POST("/api/v1/auth/signin")  // Ajustez le chemin si nécessaire
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/v1/auth/signup")  // Ajustez le chemin si nécessaire
    suspend fun registre(@Body request: RegistreRequest): Response<RegistreResponse>

    @AuthRequired
//    @GET("/laboratoires")  // Fetch the fake data
//    suspend fun Hospital(@Body request: HospitalRequest): Response<HospitalResponse>
    @GET("/laboratoires")
    suspend fun getLaboratoires(): Response<List<Laboratoire>>


    @AuthRequired
    @GET("/medecins")
    suspend fun getDoctor(): Response<List<Doctor>>
    @PUT("/medecins/{id}/like")
    suspend fun likeDoctor(@Path("id") doctorId: Int): Response<String>

    @Multipart
    @POST("api/upload")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    ): Response<ResponseBody>

    @GET("api/files")
    suspend fun getFiles(): Response<List<String>>
    @AuthRequired
    @GET("/analyses")
    suspend fun getAnalyses(): List<Analyse>
    @POST("api/registerToken")
    suspend fun registerToken(@Body tokenData: Map<String, String>): Response<Unit>

    @POST("api/paymee/create-payment")
    suspend fun createPayment(
        @Body paymentRequest: PaymentRequest
    ): Response<Map<String, Any>>
}



