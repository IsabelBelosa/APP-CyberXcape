package com.example.cyberxcape.network

import com.example.cyberxcape.model.ReservaFormulario
import com.example.cyberxcape.model.ReservaResumen
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {
    @GET("api/reservas")
    suspend fun obtenerReservas(): List<ReservaResumen>

    @GET("api/reservas/{id}")
    suspend fun obtenerReservaPorId(@Path("id") id: String): ReservaFormulario

    @POST("api/reservas")
    suspend fun crearReserva(@Body reserva: ReservaFormulario): Response<Void>

    @DELETE("api/reservas/{id}")
    suspend fun eliminarReserva(@Path("id") id: String)

    @PUT("api/reservas/{id}")
    suspend fun actualizarReserva(@Path("id") id: String, @Body reserva: ReservaFormulario)
}

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.25:3000/"
// Si se hace con emulador cambiar a 10.0.2.2
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
